package com.a304.wildworker.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends
        AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    public static final String DESTINATION_PREFIX = "/pub";
    public static final String BROKER_DEST_PREFIX = "/sub";
    public static final String BROKER_DEST_PREFIX_USER = "/queue";
    public static final String USER_DEST_PREFIX = "/user";
    public static final String WS_DEST_STATION = "/stations";

    @Value("${allowed-origins}")
    private final String[] allowedOrigins;
    private final ChannelInterceptor interceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(DESTINATION_PREFIX);
        config.enableSimpleBroker(BROKER_DEST_PREFIX, BROKER_DEST_PREFIX_USER);
        config.setUserDestinationPrefix(USER_DEST_PREFIX);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor);
    }

    @Override
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns(allowedOrigins);
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns(allowedOrigins)
                .withSockJS();
    }
}