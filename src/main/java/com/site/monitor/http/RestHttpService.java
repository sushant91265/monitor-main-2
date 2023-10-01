package com.site.monitor.http;

import com.site.monitor.service.interfaces.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestHttpService implements HttpService {
    private final RestTemplate restTemplate;

    public <T> Optional<T> makeGetCall(String url, Class<T> cls) {
        ResponseEntity<T>  responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, cls);
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        //TODO: handle 3xx, 4xx, 5xx
        if(!statusCode.is2xxSuccessful()) {
            return Optional.empty();
        }
        return Optional.ofNullable(responseEntity.getBody());
    }
}
