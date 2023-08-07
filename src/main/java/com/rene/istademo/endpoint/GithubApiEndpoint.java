package com.rene.istademo.endpoint;

import com.rene.istademo.domain.githubresponse.RepoProgLangResponse;
import com.rene.istademo.domain.githubresponse.RepositoryResponse;
import com.rene.istademo.domain.githubresponse.UserResponse;
import com.rene.istademo.endpoint.handlers.ProgLanguageResponseHandler;
import com.rene.istademo.endpoint.handlers.RepositoryResponseHandler;
import com.rene.istademo.endpoint.handlers.UserResponseHandler;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class GithubApiEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubApiEndpoint.class);

    private static final String GITHUB_ACCEPT_HEADER = "application/vnd.github+json";
    private static final String GITHUB_API_VERSION_HEADER = "X-GitHub-Api-Version";

    private HttpClientConnectionManager connectionManager;
    private CloseableHttpClient httpClient;

    @Value("${connection.timeout}")
    private Integer timeout;

    @Value("${api.token}")
    private String token;

    @Value("${api.version}")
    private String apiVersion;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.userData}")
    private String userDataUrl;

    @Value("${api.listRepositories}")
    private String listRepositoriesUrl;

    @Value("${api.listRepositoryLanguages}")
    private String listRepositoryLanguagesUrl;

    @PostConstruct
    private void createHttpClient() {
        connectionManager = new PoolingHttpClientConnectionManager();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultHeaders(createRequestHeaders())
                .setDefaultRequestConfig(config)
                .build();
    }

    public UserResponse getUserData(String username) throws IOException {
        HttpGet getRequest = new HttpGet(constructForUser(username));
        LOGGER.info("Performing synchronous GET call to " + constructForUser(username));
        return httpClient.execute(getRequest, new UserResponseHandler());
    }

    public List<RepositoryResponse> getRepositories(String username) throws IOException {
        HttpGet getRequest = new HttpGet(constructForRepositories(username));
        LOGGER.info("Performing synchronous GET call to " + constructForRepositories(username));
        return httpClient.execute(getRequest, new RepositoryResponseHandler());
    }

    public RepoProgLangResponse getProgLanguageData(String owner, String repo) throws IOException {
        HttpGet getRequest = new HttpGet(constructForListRepositoryLanguages(owner, repo));
        LOGGER.info("Performing synchronous GET call to " + constructForListRepositoryLanguages(owner, repo));
        return httpClient.execute(getRequest, new ProgLanguageResponseHandler());
    }

    private List<Header> createRequestHeaders() {
        Header acceptHeader = new BasicHeader(HttpHeaders.ACCEPT, GITHUB_ACCEPT_HEADER);
        Header githubVersionHeader = new BasicHeader(GITHUB_API_VERSION_HEADER, apiVersion);
        Header authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return Arrays.asList(acceptHeader, githubVersionHeader, authHeader);
    }

    private String constructForUser(String username) {
        String replacedUrl = StringUtils.replace(userDataUrl, "{username}", username);

        return baseUrl + replacedUrl;
    }

    private String constructForRepositories(String username) {
        String replacedUrl = StringUtils.replace(listRepositoriesUrl, "{username}", username);

        return baseUrl + replacedUrl;
    }

    private String constructForListRepositoryLanguages(String owner, String repo) {
        String replacedOwner = StringUtils.replace(listRepositoryLanguagesUrl,"{owner}", owner);
        String replacedUrl = StringUtils.replace(replacedOwner,"{repo}", repo);

        return baseUrl + replacedUrl;
    }
}
