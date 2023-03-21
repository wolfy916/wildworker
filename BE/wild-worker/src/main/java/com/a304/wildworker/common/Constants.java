package com.a304.wildworker.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class Constants {

    public static final String SESSION_NAME_USER = "user";
    public static final String SESSION_NAME_ACCESS_TOKEN = "access_token";
    public static final Long noneTitle = 1L;
    public static String URI;

    @Value("${url}${server.servlet.context-path}")
    private void setURI(String value) {
        Constants.URI = value;
    }

}