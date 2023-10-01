package com.site.monitor.schedulers;

import com.site.monitor.http.RestHttpService;
import com.site.monitor.logger.WebsiteResponseLogger;
import com.site.monitor.model.RestApiResponse;
import com.site.monitor.service.ContentVerifier;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * WebsiteMonitorJob is a Quartz Job which will be executed by the scheduler.
 */
@Slf4j
@DisallowConcurrentExecution
@Component
public class WebsiteMonitoringJob implements Job {

    @Autowired private RestHttpService httpService;
    @Autowired private ContentVerifier contentVerifier;

    @Autowired private WebsiteResponseLogger websiteResponseLogger;

    @Override
    public void execute(final JobExecutionContext context) {
        String url = context.getMergedJobDataMap().getString("url");
        String contentRequirement = context.getMergedJobDataMap().getString("contentRequirement");

        RestApiResponse response = httpService.makeGetCall(url);
        boolean isContentVerified = contentVerifier.isContentVerified(response.getData(), contentRequirement);
        boolean isWebsiteUp = response.getStatusCode() == 200;
        websiteResponseLogger.log(response, isContentVerified, isWebsiteUp);
    }
}
