package com.beite.log.search.logfilesearchdome.fileload;

import com.beite.log.search.logfilesearchdome.config.websocket.handler.LogFileHandler;
import com.beite.log.search.logfilesearchdome.resolver.LogEntryResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.ehcache.Cache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @version 1.0
 * @date 2022年05月20日 2:19 PM
 */
@Component
@Slf4j
public class FileReaderListener implements InitializingBean {

    @Value("${log.log-path}")
    private String filePath;

    /**
     * 已经存在的文件
     */
    private static final Set<String> FILE_PATH_STORAGE = Collections.synchronizedSet(new HashSet<>());


    /**
     * 文件偏移量缓存
     */
    public static Cache<String, Long> FILE_OFFSET_CACHE;

    /**
     * socket处理器
     */
    private static LogFileHandler LOG_FILE_HANDLER;

    public FileReaderListener(Cache<String, Long> fileOffsetCache,
                              LogFileHandler logFileHandler) {
        FILE_OFFSET_CACHE = fileOffsetCache;
        LOG_FILE_HANDLER = logFileHandler;
    }

    /**
     * 每半个小时 检查一次是否产生新的文件了
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshFileTask() {
        afterPropertiesSet();
    }

    @Override
    public void afterPropertiesSet() {
        File loadMainFile = new File(filePath);
        String[] files = loadMainFile.list();
        if (files != null) {

            List<String> filterFileNames = new ArrayList<>();
            for (String fileName : files) {
                if (!FILE_PATH_STORAGE.contains(fileName)) {
                    FILE_PATH_STORAGE.add(fileName);
                    filterFileNames.add(fileName);
                }

            }

            for (String fileName : filterFileNames) {
                // 创建一个线程观察文件
                new LogFileObserver(filePath, fileName).start();
            }
        }
    }

    /**
     * 日志文件观察者
     */
    static class LogFileObserver extends Thread {
        /**
         * 文件路径
         */
        private String filePath;

        /**
         * 文件名称
         */
        private String fileName;

        AtomicLong currentLineNumber = new AtomicLong(1L);

        public LogFileObserver(String filePath, String fileName) {
            this.filePath = filePath;
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                watcherLog(this.filePath, this.fileName, str -> {
                    try {
                        String escapedMessage = StringEscapeUtils.escapeHtml4(str);
                        FileReaderListener.LOG_FILE_HANDLER.sendMessageToAllClient(escapedMessage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 监控文件
         *
         * @param filePath 文件路径
         * @param fileName 文件名称
         * @param consumer 最新的监听值
         */
        private void watcherLog(String filePath, String fileName, Consumer<String> consumer) throws IOException, InterruptedException {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Paths.get(filePath).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            String filePathName = filePath + fileName;
            Cache<String, Long> fileOffsetCache = FileReaderListener.FILE_OFFSET_CACHE;

            do {
                WatchKey key = watchService.take();
                List<WatchEvent<?>> watchEvents = key.pollEvents();
                watchEvents.stream().filter(
                        i -> StandardWatchEventKinds.ENTRY_MODIFY == i.kind()
                                && fileName.equals(((Path) i.context()).getFileName().toString())
                                && "log".equals(FilenameUtils.getExtension(fileName))
                ).forEach(i -> {
                    if (i.count() > 1) {
                        // "重复事件"
                        return;
                    }

                    File configFile = Paths.get(filePath + "/" + i.context()).toFile();
                    StringBuilder str = new StringBuilder();

                    // 判断缓存是否已经这个文件的偏移量 如果不存在则设置为0
                    if (fileOffsetCache.get(filePathName) == null) {
                        fileOffsetCache.put(filePathName, 0L);
                    }

                    // 读取文件
                    FileReaderListener.FILE_OFFSET_CACHE.put(filePath + fileName, getFileContent(configFile, fileOffsetCache.get(filePath + fileName), str));

                    if (str.length() != 0) {
                        consumer.accept(str.toString());
                    }
                });
                key.reset();
            } while (true);
        }

        /**
         * 获取文件内容
         *
         * @param configFile   文件
         * @param beginPointer 从那一个字节开始
         * @param str          读取的内容
         * @return 读取到哪一个字节结束
         */
        private long getFileContent(File configFile, long beginPointer, StringBuilder str) {
            if (beginPointer < 0) {
                beginPointer = 0;
            }
            RandomAccessFile file = null;
            boolean top = true;
            try {
                file = new RandomAccessFile(configFile, "r");
                if (beginPointer > file.length()) {
                    return 0;
                }
                file.seek(beginPointer);
                String line;
                while ((line = file.readLine()) != null) {
                    if (top) {
                        top = false;
                    } else {
                        str.append("\n");
                    }
                    str.append("Line").append(currentLineNumber.get()).append(" ");
                    String lineValue = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    str.append(lineValue);
                    currentLineNumber.set(currentLineNumber.incrementAndGet());

                    // 通过解析器进行解析
                    LogEntryResolver.resolverLine(this.filePath, this.fileName, lineValue);
                }
                return file.getFilePointer();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            } finally {
                if (file != null) {
                    try {
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
