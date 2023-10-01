package com.site.monitor.logger;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class WebsiteResponseLog {
    private String url;
    private boolean contentVerificationStatus;
    private String message;
    private long responseTimeInMs;
    private boolean isWebsiteUp;

    public String describe() {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n**************************************\n");
        logMessage.append("* Website Response Log: \n");
        logMessage.append("* URL: ").append(this.getUrl()).append("\n");
        logMessage.append("* Content Verification Status: ").append(this.isContentVerificationStatus()).append("\n");
        logMessage.append("* Is Website Up: ").append(this.isWebsiteUp()).append("\n");
        logMessage.append("* Message: ").append(this.getMessage() == null ? "Success" : this.getMessage()).append("\n");
        logMessage.append("* Response Time (ms): ").append(this.getResponseTimeInMs()).append("\n");
        logMessage.append("**************************************\n");
        return logMessage.toString();
    }
}
