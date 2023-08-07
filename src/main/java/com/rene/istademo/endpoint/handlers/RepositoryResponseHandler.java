package com.rene.istademo.endpoint.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rene.istademo.domain.githubresponse.RepositoryResponse;
import com.rene.istademo.exceptions.GithubConnectionException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RepositoryResponseHandler implements ResponseHandler<List<RepositoryResponse>> {

    @Override
    public List<RepositoryResponse> handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        String statusMessage = response.getStatusLine().getReasonPhrase();

        if (statusCode == HttpStatus.SC_OK) {
            return parseResponse(response);
        } else {
            throw new GithubConnectionException("Unexpected status code of " + statusCode + " returned.", statusMessage);
        }
    }

    private List<RepositoryResponse> parseResponse(HttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RepositoryResponse[] detailsArray = objectMapper.readValue(response.getEntity().getContent(), RepositoryResponse[].class);
        return Arrays.asList(detailsArray);
    }
}
