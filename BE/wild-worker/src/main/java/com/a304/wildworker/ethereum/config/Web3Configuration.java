package com.a304.wildworker.ethereum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

//@Configuration
public class Web3Configuration {

    private final String url;

    public Web3Configuration(@Value("${web3.url}") String url) {
        this.url = url;
    }

    @Bean
    public Web3j web3() {
        return Web3j.build(new HttpService(url));
    }

}
