package com.rene.istademo.exceptions;

import org.apache.http.client.ClientProtocolException;

public class GithubConnectionException extends ClientProtocolException {

    public GithubConnectionException(String message) {
        super(message);
    }

    public GithubConnectionException(String message, String reason) {
        super(message + " Reason returned by API: " + reason);
    }
}
