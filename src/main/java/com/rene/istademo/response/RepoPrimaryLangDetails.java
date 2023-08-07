package com.rene.istademo.response;

import java.util.Objects;

public class RepoPrimaryLangDetails {

    private String name;

    public RepoPrimaryLangDetails() {
    }

    public RepoPrimaryLangDetails(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepoPrimaryLangDetails that = (RepoPrimaryLangDetails) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
