package com.beite.log.search.logfilesearchdome.config.websocket;

import com.beite.log.search.logfilesearchdome.config.websocket.handler.LogFileHandler;
import com.beite.log.search.logfilesearchdome.config.websocket.interceptor.LogFileWebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author beite_he[beite_he@insightfo.cn]
 * @author <a href="mailto:beite_he@insightfo.cn">Beite</a>
 * @date 2023年04月09日 16:56
 * @since 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logFileHandler(), "/logFileHandler")
                .addInterceptors(new LogFileWebSocketHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public LogFileHandler logFileHandler() {
        return new LogFileHandler();
    }

}
