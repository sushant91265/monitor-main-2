package com.site.monitor.http;

import com.site.monitor.model.RestApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestHttpServiceTest {

    private RestHttpService restHttpService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restHttpService = new RestHttpService(restTemplate);
    }

    @Test
    public void testMakeGetCall_Success() {
        String url = "https://example.com";
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Response data", HttpStatus.OK);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), isNull(), eq(String.class)))
                .thenReturn(responseEntity);

        RestApiResponse response = restHttpService.makeGetCall(url);

        assertEquals(url, response.getUrl());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Response data", response.getData());
        assertTrue(response.getRttInMs() >= 0);
    }

    @Test
    public void testMakeGetCall_RestClientException() {
        String url = "https://example.com";

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), isNull(), eq(String.class)))
                .thenThrow(new RestClientException("Error"));

        RestApiResponse response = restHttpService.makeGetCall(url);

        assertEquals(url, response.getUrl());
        assertEquals(500, response.getStatusCode());
        assertNull(response.getData());
        assertTrue(response.getRttInMs() >= 0);
    }
}