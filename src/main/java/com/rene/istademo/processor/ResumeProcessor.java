package com.rene.istademo.processor;

import com.rene.istademo.domain.githubresponse.ProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepoProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepositoryResponse;
import com.rene.istademo.domain.githubresponse.UserResponse;
import com.rene.istademo.response.RepoPrimaryLangDetails;
import com.rene.istademo.response.RepositoryDetails;
import com.rene.istademo.response.Resume;
import com.rene.istademo.response.ResumeLangDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResumeProcessor {

    public Resume buildResume(UserResponse userResponse, List<RepositoryDetails> repositoryDetailsList) {
        String username = userResponse.getUsername();
        String userUrl = userResponse.getWebUrl();

        List<ResumeLangDetails> resumeLangDetails = calculateLanguagePercentages(repositoryDetailsList);
        return new Resume(
                username,
                userUrl,
                repositoryDetailsList.size(),
                repositoryDetailsList,
                resumeLangDetails
        );
    }

    public RepositoryDetails getAsRepositoryDetails(RepositoryResponse repoResponse, RepoProgLangResponse repoProgLangResponse) {
        Optional<RepoPrimaryLangDetails> primaryProgLang = getPrimaryProgrammingLanguage(repoProgLangResponse.getProgLanguageResponsesList());

        return primaryProgLang.map(langDetails -> new RepositoryDetails(
                        repoResponse.getName(),
                        repoResponse.getUrl(),
                        repoResponse.getDescription(),
                        langDetails)
                )
                .orElseGet(() -> new RepositoryDetails(
                        repoResponse.getName(),
                        repoResponse.getUrl(),
                        repoResponse.getDescription())
                );
    }

    private Optional<RepoPrimaryLangDetails> getPrimaryProgrammingLanguage(List<ProgLangResponse> repoProgLang) {
        //Repositories can have no programming languages detected by Github
        if (repoProgLang.isEmpty()) {
            return Optional.empty();
        }

        List<ProgLangResponse> sortedList = repoProgLang.stream()
                .sorted(Comparator.comparing(ProgLangResponse::getBytes).reversed())
                .collect(Collectors.toList());

        ProgLangResponse primaryLang = sortedList.get(0);

        return Optional.of(new RepoPrimaryLangDetails(primaryLang.getName()));
    }

    private List<ResumeLangDetails> calculateLanguagePercentages(List<RepositoryDetails> repositoryDetails) {
        List<ResumeLangDetails> resumeLangDetailList = new ArrayList<>();

        //Get number of occurrences for each of the primary programming language of all repositories
        Map<String, Integer> occurencesMap = new HashMap<>();
        for (RepositoryDetails repository : repositoryDetails) {
            if (Objects.nonNull(repository.getPrimaryProgLanguage())) {
                String progLangName = repository.getPrimaryProgLanguage().getName();
                occurencesMap.merge(progLangName, 1, Integer::sum);
            }
        }

        //Calculate the percentage of occurrences for each programming language
        for (Map.Entry<String, Integer> entry : occurencesMap.entrySet()) {
            String langName = entry.getKey();
            //Calculate percentage among all repositories, even if there is not a primary prog language for one repo
            double percentage = (entry.getValue().doubleValue() / repositoryDetails.size()) * 100;
            ResumeLangDetails resumeLangDetails = new ResumeLangDetails(langName, BigDecimal.valueOf(percentage).setScale(2, RoundingMode.HALF_UP));
            resumeLangDetailList.add(resumeLangDetails);
        }

        return resumeLangDetailList.stream()
                .sorted(Comparator.comparing(ResumeLangDetails::getPercentage).reversed())
                .collect(Collectors.toList());
    }
}
