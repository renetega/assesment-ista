package com.rene.istademo.service;

import com.rene.istademo.domain.Request;
import com.rene.istademo.domain.githubresponse.RepoProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepositoryResponse;
import com.rene.istademo.domain.githubresponse.UserResponse;
import com.rene.istademo.processor.ResumeProcessor;
import com.rene.istademo.response.RepositoryDetails;
import com.rene.istademo.response.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private GithubService githubService;

    @Autowired
    private ResumeProcessor resumeProcessor;

    public Resume getResumeFromRequest(Request request) throws IOException {
        UserResponse userResponse = githubService.getUserDetails(request.getAccount());
        List<RepositoryResponse> repositories = githubService.getRepositories(request.getAccount());

        List<RepositoryDetails> repositoryDetailList = new ArrayList<>();
        for (RepositoryResponse repository: repositories) {
            repositoryDetailList.add(getRepositoryDetails(repository));
        }

        return resumeProcessor.buildResume(userResponse, repositoryDetailList);
    }

    private RepositoryDetails getRepositoryDetails(RepositoryResponse repository) throws IOException {
        String owner = repository.getOwner().getUsername();

        RepoProgLangResponse repoProgLangResponse = githubService.getProgrammingLanguages(owner, repository.getName());

        return resumeProcessor.getAsRepositoryDetails(repository, repoProgLangResponse);
    }

}
