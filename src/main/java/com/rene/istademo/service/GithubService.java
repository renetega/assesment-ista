package com.rene.istademo.service;

import com.rene.istademo.domain.githubresponse.RepoProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepositoryResponse;
import com.rene.istademo.domain.githubresponse.UserResponse;
import com.rene.istademo.endpoint.GithubApiEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GithubService {

    @Autowired
    private GithubApiEndpoint githubApiEndpoint;

    public UserResponse getUserDetails(String username) throws IOException {
        return githubApiEndpoint.getUserData(username);
    }

    public List<RepositoryResponse> getRepositories(String username) throws IOException {
        return githubApiEndpoint.getRepositories(username);
    }

    public RepoProgLangResponse getProgrammingLanguages(String owner, String repo) throws IOException {
        return githubApiEndpoint.getProgLanguageData(owner, repo);
    }
}
