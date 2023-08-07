package com.rene.istademo.controller.advice;

import com.rene.istademo.exceptions.GithubConnectionException;
import com.rene.istademo.exceptions.UsernameDoesNotExistException;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResumeExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResumeExceptionHandler.class);

    @ExceptionHandler(UsernameDoesNotExistException.class)
    protected ResponseEntity<ErrorDetails> handleUserNotFound(UsernameDoesNotExistException e) {
        ErrorDetails errorDetails = generateErrorDetails(e, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler({GithubConnectionException.class})
    protected ResponseEntity<ErrorDetails> handleGithubConnectionException(GithubConnectionException e) {
        ErrorDetails errorDetails = generateErrorDetails(e, e.getMessage());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDetails);
    }

    @ExceptionHandler({ConnectTimeoutException.class})
    protected ResponseEntity<ErrorDetails> handleConnectTimeoutException(ConnectTimeoutException e) {
        ErrorDetails errorDetails = generateErrorDetails(e, "Could not connect to GitHub's API");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDetails);
    }

    //Override ResponseEntityExceptionHandler method to generate custom message when 'account' param is missing
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = generateErrorDetails(ex, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    private ErrorDetails generateErrorDetails(Exception e, String message) {
        LOGGER.error("Handling " + e.getClass().getCanonicalName() +":", e);
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(message);

        return errorDetails;
    }

}
