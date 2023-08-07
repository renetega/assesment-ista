package com.rene.istademo.domain.githubresponse;

import java.util.List;

public class RepoProgLangResponse {

    private List<ProgLangResponse> progLangResponsesList;

    public RepoProgLangResponse() {
    }

    public RepoProgLangResponse(List<ProgLangResponse> progLangResponsesList) {
        this.progLangResponsesList = progLangResponsesList;
    }

    public List<ProgLangResponse> getProgLanguageResponsesList() {
        return progLangResponsesList;
    }

    public void setProgLanguageResponsesList(List<ProgLangResponse> progLangResponsesList) {
        this.progLangResponsesList = progLangResponsesList;
    }
}
