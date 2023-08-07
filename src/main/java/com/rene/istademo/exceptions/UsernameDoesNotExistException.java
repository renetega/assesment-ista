package com.rene.istademo.exceptions;

import org.apache.http.client.ClientProtocolException;

public class UsernameDoesNotExistException extends ClientProtocolException {

    public UsernameDoesNotExistException(String message, String externalMessage) {
        super(message + " Message from GitHub API: " + externalMessage);
    }
}
