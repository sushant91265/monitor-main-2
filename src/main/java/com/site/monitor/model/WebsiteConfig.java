package com.site.monitor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the configuration of a single website.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteConfig {
    private Long id;
    private String url;
    @JsonProperty("content_requirement")
    private String contentRequirement;
    @JsonProperty("check_interval_seconds")
    private Integer checkIntervalSeconds = 0;
}
