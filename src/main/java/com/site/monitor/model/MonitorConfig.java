package com.site.monitor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * This class represents the configuration of the complete monitoring system.
 */
@Data
public class MonitorConfig {
    private List<WebsiteConfig> websites;
    @JsonProperty("default_check_interval_seconds")
    private Integer defaultCheckIntervalSeconds;
}

