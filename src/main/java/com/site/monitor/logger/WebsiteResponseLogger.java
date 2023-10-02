package com.site.monitor.logger;

import com.site.monitor.model.RestApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebsiteResponseLogger {

    public void log(RestApiResponse RestApiResponse, boolean isContentVerified, boolean isWebsiteUp) {
        WebsiteResponseLog responseLog = toWebsiteResponseLog(RestApiResponse, isContentVerified, isWebsiteUp);
        log.info(responseLog.describe());
    }

    private WebsiteResponseLog toWebsiteResponseLog(RestApiResponse RestApiResponse, boolean isContentVerified, boolean isWebsiteUp) {
        WebsiteResponseLog.WebsiteResponseLogBuilder builder = new WebsiteResponseLog.WebsiteResponseLogBuilder();
        builder.url(RestApiResponse.getUrl());
        builder.contentVerificationStatus(isContentVerified);
        builder.message(RestApiResponse.getMessage());
        builder.responseTimeInMs(RestApiResponse.getRttInMs());
        builder.isWebsiteUp(isWebsiteUp);
        return builder.build();
    }

}
