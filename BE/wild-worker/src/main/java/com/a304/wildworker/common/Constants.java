package com.a304.wildworker.common;

public final class Constants {

    public static final String SESSION_NAME_USER = "user";
    public static final String SESSION_NAME_ACCESS_TOKEN = "access_token";
    public static final String SESSION_NAME_PREV_PAGE = "prev_page";
    public static final Long noneTitle = 1L;

    // The HTTP Set-Cookie header field name.
    // See Also: Section 4.2.2 of RFC 2109
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String KEY_SESSION_ID = "SESSION";

    // 스케줄러 동작 주기 (minute)
    public static final int INTERVAL = 10;

    // 역 범위(반지름) (meter)
    public static final int STATION_RANGE = 250;

    // 채굴 금액
    public static final long AMOUNT_MANUAL_MINE = 100L;
    public static final long AMOUNT_AUTO_MINE = 100L;

    // 루트 Station
    public static final Long ROOT_STATION_ID = -1L;
}