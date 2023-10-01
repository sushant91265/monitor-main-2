package com.site.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the response of a single website.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteResponse {
    private String url;
    private Boolean isContentVerified = false;
    private Long responseTime = 0L;
    private Integer responseCode;
    private String errorMessage;

    public String describe() {
        return String.format("URL: [%s], isContentVerified: [%s], responseTime: [%d], responseCode: [%d], errorMessage: [ERROR: %s]",
                url, isContentVerified, responseTime, responseCode, errorMessage);
    }
}
