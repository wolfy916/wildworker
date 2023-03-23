package com.a304.wildworker.common;

public final class Constants {

    public static final String SESSION_NAME_USER = "user";
    public static final String SESSION_NAME_ACCESS_TOKEN = "access_token";
    public static final String SESSION_NAME_PREV_PAGE = "prev_page";
    public static final Long noneTitle = 1L;

    // The HTTP Set-Cookie header field name.
    // See Also: Section 4.2.2 of RFC 2109
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String JSESSIONID = "JSESSIONID";   //TODO: chage spring session

    public static final int STATION_RANGE = 250; //역 범위(반지름) meter
}