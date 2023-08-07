package com.rene.istademo.endpoint.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rene.istademo.domain.githubresponse.UserResponse;
import com.rene.istademo.exceptions.GithubConnectionException;
import com.rene.istademo.exceptions.UsernameDoesNotExistException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class UserResponseHandler implements ResponseHandler<UserResponse> {

    @Override
    public UserResponse handleResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        String statusMessage = response.getStatusLine().getReasonPhrase();

        if (statusCode == HttpStatus.SC_OK) {
            return parseResponse(response);
        } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
            throw new UsernameDoesNotExistException("Provided username does not exist.", statusMessage);
        } else {
            throw new GithubConnectionException("Unexpected status code of " + statusCode + " returned.", statusMessage);
        }
    }

    private UserResponse parseResponse(HttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getEntity().getContent(), UserResponse.class);
    }
}
