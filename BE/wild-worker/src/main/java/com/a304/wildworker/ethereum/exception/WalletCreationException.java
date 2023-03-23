package com.a304.wildworker.ethereum.exception;

public class WalletCreationException extends RuntimeException {

    public WalletCreationException() {
        super("지갑 생성에 문제가 발생했습니다.");
    }
}
