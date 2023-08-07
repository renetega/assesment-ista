package com.rene.istademo.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Resume {

    private String username;
    private String websiteUrl;
    private Integer numberOfRepos;
    private List<RepositoryDetails> repositoryList;
    private List<ResumeLangDetails> languageList;

    public Resume() {
    }

    public Resume(String username, String websiteUrl, Integer numberOfRepos, List<RepositoryDetails> repositoryList, List<ResumeLangDetails> languageList) {
        this.username = username;
        this.websiteUrl = websiteUrl;
        this.numberOfRepos = numberOfRepos;
        this.repositoryList = repositoryList;
        this.languageList = languageList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getNumberOfRepos() {
        return numberOfRepos;
    }

    public void setNumberOfRepos(Integer numberOfRepos) {
        this.numberOfRepos = numberOfRepos;
    }

    @XmlElementWrapper(name = "repositories")
    @XmlElement(name = "repositoryDetail")
    public List<RepositoryDetails> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<RepositoryDetails> repositoryList) {
        this.repositoryList = repositoryList;
    }

    @XmlElementWrapper(name = "languages")
    @XmlElement(name = "languageDetail")
    public List<ResumeLangDetails> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<ResumeLangDetails> languageList) {
        this.languageList = languageList;
    }
}
