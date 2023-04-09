package com.beite.log.search.logfilesearchdome.config.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月09日 16:59
 * @since 1.0
 */
public class LogFileHandler implements WebSocketHandler {
    private static Map<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();
    public LogFileHandler() {
        System.out.println("处理器正在创建");
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 当建立连接时，执行此方法
        SESSION_MAP.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 处理消息
        System.out.println(message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 处理传输错误
        SESSION_MAP.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 当连接关闭时，执行此方法
        SESSION_MAP.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToClient(String sessionId, String message) throws IOException {
        WebSocketSession webSocketSession = SESSION_MAP.get(sessionId);
        webSocketSession.sendMessage(new TextMessage(message));
    }

    public void sendMessageToAllClient(String message) throws IOException {
        SESSION_MAP.values().forEach(item -> {
            try {
                item.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
