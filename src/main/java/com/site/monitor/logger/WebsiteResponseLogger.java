package com.site.monitor.logger;

import com.site.monitor.model.RestApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebsiteResponseLogger {

    public void log(RestApiResponse restApiResponse, boolean isContentVerified, boolean isWebsiteUp) {
        WebsiteResponseLog responseLog = toWebsiteResponseLog(restApiResponse, isContentVerified, isWebsiteUp);
        log.info(responseLog.describe());
    }

    private WebsiteResponseLog toWebsiteResponseLog(RestApiResponse restApiResponse, boolean isContentVerified, boolean isWebsiteUp) {
        WebsiteResponseLog.WebsiteResponseLogBuilder builder = new WebsiteResponseLog.WebsiteResponseLogBuilder();
        builder.url(restApiResponse.getUrl());
        builder.contentVerificationStatus(isContentVerified);
        builder.message(restApiResponse.getMessage());
        builder.responseTimeInMs(restApiResponse.getRttInMs());
        builder.isWebsiteUp(isWebsiteUp);
        return builder.build();
    }

}
