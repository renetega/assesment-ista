package com.rene.istademo.domain;

import org.springframework.http.MediaType;

public class Request {
    private String account;

    private MediaType producedMediaType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public MediaType getProducedMediaType() {
        return producedMediaType;
    }

    public void setProducedMediaType(MediaType producedMediaType) {
        this.producedMediaType = producedMediaType;
    }
}
