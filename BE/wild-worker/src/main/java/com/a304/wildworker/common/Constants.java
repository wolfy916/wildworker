package com.a304.wildworker.common;

public final class Constants {

    //key of Session cookie
    public static final String KEY_SESSION_ID = "SESSION";

    //Session attribute names
    public static final String SESSION_NAME_ACCESS_TOKEN = "access_token";
    public static final String SESSION_NAME_PREV_PAGE = "prev_page";

    //타이틀 '없음' id
    public static final Long NONE_TITLE_ID = 1L;

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

    // 칭호 아이디
    public static final Long RISK_TAKER = 0L;
    public static final Long RUNNER = 1L;
    public static final Long POOR = 2L;
    public static final Long RICH = 3L;
    public static final Long LOOSER = 4L;
    public static final Long WINNER = 5L;

    // 칭호 조건
    public static final int CONDITION_RISK_TAKER = 20;
    public static final int CONDITION_RUNNER = 20;
    public static final int CONDITION_POOR = 0;
    public static final int CONDITION_RICH = 100000;
    public static final int CONDITION_LOOSER = 10;
    public static final int CONDITION_WINNER = 10;

}