package com.a304.wildworker.ethereum.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

@Slf4j
@Configuration
public class Web3Configuration {

    private final String url;

    public Web3Configuration(@Value("${web3.url}") String url) {
        this.url = url;
        log.info("web3 url injected :: {}", url);
    }

    @Bean
    public Web3j web3() {
        return Admin.build(new HttpService(url));
    }

    @Bean
    public Credentials credentials(@Value("${web3.root-private}") String privateKey) {
        return Credentials.create(privateKey);
    }

}
