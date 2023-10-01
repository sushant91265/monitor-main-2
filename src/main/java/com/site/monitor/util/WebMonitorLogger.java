package com.site.monitor.util;

import com.site.monitor.model.WebsiteResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * WebMonitorLogger class is used to log the website response.
 * It has separate logging styles mentioned in logback configuration file.
 */
@Slf4j
public class WebMonitorLogger {
    public static void logWebsiteResponse(WebsiteResponse websiteResponse) {
        log.info(websiteResponse.describe());
    }
}
