package ru.kphu.bot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.kphu.bot.handlers.AuthHandshakeHandler;
import ru.kphu.bot.handlers.WebSocketMessagesHandler;


@Configuration
@Profile("web")
class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketMessagesHandler handler;
    private final AuthHandshakeHandler handshakeHandler;

    public WebSocketConfig(WebSocketMessagesHandler handler, AuthHandshakeHandler handshakeHandler) {
        this.handler = handler;
        this.handshakeHandler = handshakeHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler, "/game")
                .setHandshakeHandler(handshakeHandler)
                .withSockJS();
    }
}
