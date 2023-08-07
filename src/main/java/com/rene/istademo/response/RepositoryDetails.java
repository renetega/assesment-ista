package com.rene.istademo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@XmlRootElement
public class RepositoryDetails {

    private String name;
    private String url;
    private String description;

    public RepositoryDetails() {
    }

    private RepoPrimaryLangDetails primaryProgLanguage;

    public RepositoryDetails(String name, String url, String description, RepoPrimaryLangDetails primaryProgLanguage) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.primaryProgLanguage = primaryProgLanguage;
    }

    public RepositoryDetails(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    @XmlTransient
    public RepoPrimaryLangDetails getPrimaryProgLanguage() {
        return primaryProgLanguage;
    }

    public void setPrimaryProgLanguage(RepoPrimaryLangDetails primaryProgLanguage) {
        this.primaryProgLanguage = primaryProgLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryDetails that = (RepositoryDetails) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url) && Objects.equals(description, that.description) && Objects.equals(primaryProgLanguage, that.primaryProgLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, description, primaryProgLanguage);
    }
}
