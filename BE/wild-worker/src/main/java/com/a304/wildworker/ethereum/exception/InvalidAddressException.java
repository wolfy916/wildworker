package com.a304.wildworker.ethereum.exception;

public class InvalidAddressException extends IllegalArgumentException {

    public InvalidAddressException() {
        super("유효하지 않은 주소입니다.");
    }
}
