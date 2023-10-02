package com.site.monitor.schedulers;

import com.site.monitor.http.RestHttpService;
import com.site.monitor.logger.WebsiteResponseLogger;
import com.site.monitor.model.RestApiResponse;
import com.site.monitor.service.ContentVerifierService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * WebsiteMonitorJob is a Quartz Job which will be executed by the scheduler.
 */
@Slf4j
@DisallowConcurrentExecution
@Component
public class WebsiteMonitoringJob implements Job {

    private RestHttpService httpService;
    private ContentVerifierService contentVerifierService;
    private WebsiteResponseLogger websiteResponseLogger;
    private static final int SUCCESS_CODE = 200;


    @Override
    public void execute(final JobExecutionContext context) {
        String url = context.getMergedJobDataMap().getString("url");
        String contentRequirement = context.getMergedJobDataMap().getString("contentRequirement");

        RestApiResponse response = httpService.makeGetCall(url);
        boolean isContentVerified = contentVerifierService.isContentVerified(response.getData(), contentRequirement);
        boolean isWebsiteUp = response.getStatusCode() == SUCCESS_CODE;
        websiteResponseLogger.log(response, isContentVerified, isWebsiteUp);
    }

    @Autowired
    public void setHttpService(RestHttpService httpService) {
        this.httpService = httpService;
    }

    @Autowired
    public void setContentVerifierService(ContentVerifierService contentVerifierService) {
        this.contentVerifierService = contentVerifierService;
    }

    @Autowired
    public void setWebsiteResponseLogger(WebsiteResponseLogger websiteResponseLogger) {
        this.websiteResponseLogger = websiteResponseLogger;
    }
}
