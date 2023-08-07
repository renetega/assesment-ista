package com.rene.istademo.controller;

import com.rene.istademo.domain.Request;
import com.rene.istademo.response.Resume;
import com.rene.istademo.service.GithubService;
import com.rene.istademo.service.ResumeService;
import com.rene.istademo.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private GithubService githubService;

    @GetMapping(path = "/resume", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE } )
    public ResponseEntity<Resume> getResume(@RequestParam String account,
                                            @RequestParam(required = false) String mediaType,
                                            @RequestHeader Map<String, String> headers) throws IOException {

        Request request = RequestUtils.buildRequest(account, mediaType, headers);
        Resume resume = resumeService.getResumeFromRequest(request);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, request.getProducedMediaType().toString());

        return ResponseEntity.ok()
                .contentType(request.getProducedMediaType())
                .headers(responseHeaders)
                .body(resume);
    }
}
