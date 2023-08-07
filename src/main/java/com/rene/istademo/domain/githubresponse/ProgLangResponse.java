package com.rene.istademo.domain.githubresponse;

public class ProgLangResponse {

    private String name;

    private Integer bytes;

    public ProgLangResponse() {
    }

    public ProgLangResponse(String name, Integer bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }
}
