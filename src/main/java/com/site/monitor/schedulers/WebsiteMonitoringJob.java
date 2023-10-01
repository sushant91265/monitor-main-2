package com.site.monitor.schedulers;

import com.site.monitor.http.RestHttpService;
import com.site.monitor.service.ContentVerifier;
import com.site.monitor.service.interfaces.HttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * WebsiteMonitorJob is a Quartz Job which will be executed by the scheduler.
 */
@Slf4j
@DisallowConcurrentExecution
@Component
public class WebsiteMonitoringJob implements Job {

    @Autowired private RestHttpService httpService;
    @Autowired private ContentVerifier contentVerifier;

    @Override
    public void execute(final JobExecutionContext context) {
        String url = context.getMergedJobDataMap().getString("url");
        String contentRequirement = context.getMergedJobDataMap().getString("contentRequirement");
        Optional<String> response = httpService.makeGetCall(url, String.class);
        //TODO: response.get with null check
        boolean isContentVerified = contentVerifier.isContentVerified(response.get(), contentRequirement);
        log.info("Website {} is {}", url, isContentVerified ? "UP" : "DOWN");
    }
}
