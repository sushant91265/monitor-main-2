package com.site.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the response of a single website.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestApiResponse {
    String url;
    String data;
    int statusCode;
    String message;
    long rttInMs;
}
