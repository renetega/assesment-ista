package com.rene.istademo;

import com.rene.istademo.domain.githubresponse.ProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepoProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepositoryResponse;
import com.rene.istademo.domain.githubresponse.UserResponse;
import com.rene.istademo.processor.ResumeProcessor;
import com.rene.istademo.response.RepoPrimaryLangDetails;
import com.rene.istademo.response.RepositoryDetails;
import com.rene.istademo.response.Resume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ResumeProcessorTests {

    ResumeProcessor resumeProcessor = new ResumeProcessor();

    @Test
    public void buildResume_Correct() {
        //Set up
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("test");
        userResponse.setWebUrl("testwebsite");

        RepoPrimaryLangDetails primaryLang1 = new RepoPrimaryLangDetails();
        primaryLang1.setName("primaryLang1");

        RepoPrimaryLangDetails primaryLang2 = new RepoPrimaryLangDetails();
        primaryLang1.setName("primaryLang2");

        RepositoryDetails repo1 = new RepositoryDetails();
        repo1.setName("repo1");
        repo1.setDescription("desc1");
        repo1.setUrl("url1");
        repo1.setPrimaryProgLanguage(primaryLang1);

        RepositoryDetails repo2 = new RepositoryDetails();
        repo2.setName("repo2");
        repo2.setDescription("desc2");
        repo2.setUrl("url2");
        repo2.setPrimaryProgLanguage(primaryLang2);

        //Test method
        Resume resume = resumeProcessor.buildResume(userResponse, Arrays.asList(repo1, repo2));

        //Assert
        assertEquals(userResponse.getUsername(), resume.getUsername());
        assertEquals(userResponse.getWebUrl(), resume.getWebsiteUrl());
        assertEquals(2, resume.getNumberOfRepos());

        Optional<RepositoryDetails> repo1Opt = resume.getRepositoryList().stream().filter(d -> d.getName().equals("repo1")).findFirst();
        assertTrue(repo1Opt.isPresent());
        assertEquals(repo1, repo1Opt.get());

        Optional<RepositoryDetails> repo2Opt = resume.getRepositoryList().stream().filter(d -> d.getName().equals("repo2")).findFirst();
        assertTrue(repo2Opt.isPresent());
        assertEquals(repo2, repo2Opt.get());

        assertEquals(2, resume.getLanguageList().size());
        assertEquals(BigDecimal.valueOf(50d).setScale(2, RoundingMode.HALF_UP), resume.getLanguageList().get(0).getPercentage());
        assertEquals(BigDecimal.valueOf(50d).setScale(2, RoundingMode.HALF_UP), resume.getLanguageList().get(1).getPercentage());
    }

    @Test
    public void buildResume_Correct_OnlyOneRepoWithLanguage() {
        //Set up
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("test");
        userResponse.setWebUrl("testwebsite");

        RepoPrimaryLangDetails primaryLang1 = new RepoPrimaryLangDetails();
        primaryLang1.setName("primaryLang1");

        RepositoryDetails repo1 = new RepositoryDetails();
        repo1.setName("repo1");
        repo1.setDescription("desc1");
        repo1.setUrl("url1");
        repo1.setPrimaryProgLanguage(primaryLang1);

        RepositoryDetails repo2 = new RepositoryDetails();
        repo2.setName("repo2");
        repo2.setDescription("desc2");
        repo2.setUrl("url2");

        //Test method
        Resume resume = resumeProcessor.buildResume(userResponse, Arrays.asList(repo1, repo2));

        //Assert
        assertEquals(userResponse.getUsername(), resume.getUsername());
        assertEquals(userResponse.getWebUrl(), resume.getWebsiteUrl());
        assertEquals(2, resume.getNumberOfRepos());

        Optional<RepositoryDetails> repo1Opt = resume.getRepositoryList().stream().filter(d -> d.getName().equals("repo1")).findFirst();
        assertTrue(repo1Opt.isPresent());
        assertEquals(repo1, repo1Opt.get());

        Optional<RepositoryDetails> repo2Opt = resume.getRepositoryList().stream().filter(d -> d.getName().equals("repo2")).findFirst();
        assertTrue(repo2Opt.isPresent());
        assertEquals(repo2, repo2Opt.get());

        assertEquals(1, resume.getLanguageList().size());
        //The percentage of occurrences is calculated taking into consideration all the repos, even if there is no
        //primary programming language for it
        assertEquals(BigDecimal.valueOf(50d).setScale(2, RoundingMode.HALF_UP), resume.getLanguageList().get(0).getPercentage());
    }

    @Test
    public void generateRepositoryDetails_Correctly() {
        //Set up
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("owner");
        userResponse.setWebUrl("ownerUrl");

        RepositoryResponse repoResponse = new RepositoryResponse();
        repoResponse.setOwner(userResponse);
        repoResponse.setName("name");
        repoResponse.setDescription("description");
        repoResponse.setUrl("url");

        ProgLangResponse progLangResponse1 = new ProgLangResponse();
        progLangResponse1.setName("ProgLang 1");
        progLangResponse1.setBytes(1000);

        ProgLangResponse progLangResponse2 = new ProgLangResponse();
        progLangResponse2.setName("ProgLang 2");
        progLangResponse2.setBytes(50000);

        RepoProgLangResponse repoProgLangResponse = new RepoProgLangResponse();
        repoProgLangResponse.setProgLanguageResponsesList(Arrays.asList(progLangResponse1, progLangResponse2));

        //Test method
        RepositoryDetails repositoryDetails = resumeProcessor.getAsRepositoryDetails(repoResponse, repoProgLangResponse);

        //Assert
        assertEquals(repoResponse.getName(), repositoryDetails.getName());
        assertEquals(repoResponse.getUrl(), repositoryDetails.getUrl());
        assertEquals(repoResponse.getDescription(), repositoryDetails.getDescription());
        assertEquals(progLangResponse2.getName(), repositoryDetails.getPrimaryProgLanguage().getName());
    }

    @Test
    public void generateRepositoryDetails_Correct_NoLanguages() {
        //Set up
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("owner");
        userResponse.setWebUrl("ownerUrl");

        RepositoryResponse repoResponse = new RepositoryResponse();
        repoResponse.setOwner(userResponse);
        repoResponse.setName("name");
        repoResponse.setDescription("description");
        repoResponse.setUrl("url");


        RepoProgLangResponse repoProgLangResponse = new RepoProgLangResponse();
        repoProgLangResponse.setProgLanguageResponsesList(Collections.emptyList());

        //Test method
        RepositoryDetails repositoryDetails = resumeProcessor.getAsRepositoryDetails(repoResponse, repoProgLangResponse);

        //Assert
        assertEquals(repoResponse.getName(), repositoryDetails.getName());
        assertEquals(repoResponse.getUrl(), repositoryDetails.getUrl());
        assertEquals(repoResponse.getDescription(), repositoryDetails.getDescription());
        assertNull(repositoryDetails.getPrimaryProgLanguage());
    }
}
