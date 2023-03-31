package com.a304.wildworker.common;

public final class Constants {

    //key of Session cookie
    public static final String KEY_SESSION_ID = "SESSION";

    //Session attribute names
    public static final String SESSION_NAME_ACCESS_TOKEN = "access_token";
    public static final String SESSION_NAME_PREV_PAGE = "prev_page";

    //타이틀 '없음' id
    public static final Long noneTitle = 1L;

    // 스케줄러 동작 주기 (minute)
    public static final int INTERVAL = 10;

    // 역
    public static final int STATION_RANGE = 250;    // 역 범위(반지름) (meter)
    public static final Long ROOT_STATION_ID = -1L; // 루트 Station ID
    public static final int STATION_COUNT = 51; // 역 개수

    // 채굴
    public static final int SELL_LIMIT = 100;   //수동 채굴 가능한 종이 개수
    public static final long AMOUNT_MANUAL_MINE = 100L; //수동 채굴 금액
    public static final long AMOUNT_AUTO_MINE = 100L;   //자동 채굴 금액

}