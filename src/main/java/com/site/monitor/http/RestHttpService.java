package com.site.monitor.http;

import com.site.monitor.model.RestApiResponse;
import com.site.monitor.service.interfaces.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * RestHttpService class is used to make REST calls.
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestHttpService implements HttpService {
    private final RestTemplate restTemplate;

    public RestApiResponse makeGetCall(String url) {
        var responseBuilder = RestApiResponse.builder().url(url);
        long startTime = System.currentTimeMillis();

        try {
            ResponseEntity<String>  responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            responseBuilder.statusCode(responseEntity.getStatusCode().value());
            responseBuilder.data(responseEntity.getBody());
        } catch (RestClientException exception) {
            log.error("Error while making GET call to {}", url, exception);
            responseBuilder.statusCode(500);
            responseBuilder.message(exception.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();
            responseBuilder.rttInMs(endTime - startTime);
        }
        return responseBuilder.build();
    }
}
