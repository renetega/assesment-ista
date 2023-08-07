package com.rene.istademo.util;

import com.rene.istademo.domain.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestUtils {

    public static Request buildRequest(String account, String mediaType, Map<String, String> headersMap) {
        Request request = new Request();

        request.setAccount(account);

        MediaType producedType = parseProducedMediaType(mediaType, headersMap).orElseGet(() -> MediaType.APPLICATION_JSON);
        request.setProducedMediaType(producedType);
        return request;
    }

    public static Optional<MediaType> parseProducedMediaType(String mediaTypeString, Map<String, String> headersMap) {
        if (headersMap.containsKey(HttpHeaders.ACCEPT.toLowerCase())) {
            return Optional.of(MediaType.valueOf(headersMap.get(HttpHeaders.ACCEPT.toLowerCase())));
        } else if (Objects.nonNull(mediaTypeString)) {
            return Optional.of(MediaType.valueOf(mediaTypeString));
        }

        return Optional.empty();
    }
}
