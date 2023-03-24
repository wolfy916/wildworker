package com.a304.wildworker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    @Value("${allowed-origins}")
    private final String[] allowedOrigins;
    private final HttpHandshakeInterceptor interceptor;

    private final ChannelInterceptor interceptor2;

    @Override
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(interceptor)
                .setAllowedOrigins(allowedOrigins)
                .withSockJS();
        registry.addEndpoint("/ws")
                .addInterceptors(interceptor)
                .setAllowedOrigins(allowedOrigins);
        registry.addEndpoint("/secured/ws")
                .setAllowedOrigins(allowedOrigins);
        registry.addEndpoint("/secured/ws")
                .setAllowedOrigins(allowedOrigins)
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/pub");
        config.enableSimpleBroker("/sub", "/queue");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor2);
    }
}