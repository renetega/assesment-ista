package com.rene.istademo.endpoint.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rene.istademo.domain.githubresponse.ProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepoProgLangResponse;
import com.rene.istademo.exceptions.GithubConnectionException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgLanguageResponseHandler implements ResponseHandler<RepoProgLangResponse> {

    @Override
    public RepoProgLangResponse handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        String statusMessage = response.getStatusLine().getReasonPhrase();

        if (statusCode == HttpStatus.SC_OK) {
            return parseResponse(response);
        } else {
            throw new GithubConnectionException("Unexpected status code of " + statusCode + " returned.", statusMessage);
        }

    }

    private RepoProgLangResponse parseResponse(HttpResponse response) throws IOException {
        List<ProgLangResponse> progLanguages = new ArrayList<>();

        /*
        Github API returns an object with each of the programming language being the name of a property
        and the value being the number of bytes in the repo being written in that language.

        This function maps that JSON to individual ProgLangResponse POJOs for each property in the JSON object.
        */
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
        jsonNode.fields().forEachRemaining(field -> {
            ProgLangResponse progLangResponse = new ProgLangResponse(field.getKey(), field.getValue().asInt());
            progLanguages.add(progLangResponse);
        });

        return new RepoProgLangResponse(progLanguages);
    }
}
