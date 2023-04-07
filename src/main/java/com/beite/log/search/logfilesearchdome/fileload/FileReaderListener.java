package com.beite.log.search.logfilesearchdome.fileload;

import com.beite.log.search.logfilesearchdome.resolver.LogEntryResolver;
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
import java.util.Arrays;
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
public class FileReaderListener implements InitializingBean {

    @Value("${log.log-path}")
    private String filePath;

    /**
     * 已经存在的文件
     */
    private static final Set<String> FILE_PATH_STORAGE = Collections.synchronizedSet(new HashSet<>());

    /**
     * 每天的12点05分刷新一下日志文件的监控,因为新的一天会产生新的日志文件
     */
    @Scheduled(cron = "0 5 12 * * ?")
    public void refreshFileTask() {
        afterPropertiesSet();
    }

    @Override
    public void afterPropertiesSet(){
        File loadMainFile = new File(filePath);
        String[] files = loadMainFile.list();
        if (files != null) {
            List<String> fileNames = Arrays.asList(files);

            List<String> filterFileNames = new ArrayList<>();
            for (String fileName : fileNames) {
                if (!FILE_PATH_STORAGE.contains(fileName)) {
                    FILE_PATH_STORAGE.add(fileName);
                    filterFileNames.add(fileName);
                }

            }

            for (String fileName : filterFileNames) {
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
                    //System.out.println("文件路径:" + filePath + "文件名称:" + fileName + "最新监听值:" + str);
                });
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 监控文件
         * @param filePath 文件路径
         * @param fileName 文件名称
         * @param consumer 最新的监听值
         */
        private void watcherLog(String filePath, String fileName, Consumer<String> consumer) throws IOException, InterruptedException {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Paths.get(filePath).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            // 文件读取偏移量
            AtomicLong lastPointer = new AtomicLong(0L);

            do {
                WatchKey key = watchService.take();
                List<WatchEvent<?>> watchEvents = key.pollEvents();
                watchEvents.stream().filter(
                        i -> StandardWatchEventKinds.ENTRY_MODIFY == i.kind()
                                && fileName.equals(((Path) i.context()).getFileName().toString())
                ).forEach(i -> {
                    if (i.count() > 1) {
                        // "重复事件"
                        return;
                    }

                    File configFile = Paths.get(filePath + "/" + i.context()).toFile();
                    StringBuilder str = new StringBuilder();
                    // 读取文件
                    lastPointer.set(getFileContent(configFile, lastPointer.get(), str));

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
                    str.append("Line").append(currentLineNumber.get()).append("\t");
                    str.append(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                    currentLineNumber.set(currentLineNumber.incrementAndGet());

                    // 通过解析器进行解析
                    LogEntryResolver.resolverLine(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
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
