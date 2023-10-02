package com.site.monitor.service;

import com.site.monitor.model.MonitorConfig;
import com.site.monitor.model.WebsiteConfig;
import com.site.monitor.service.interfaces.ConfigurationReader;
import com.site.monitor.service.interfaces.WebsiteMonitorScheduler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WebsiteMonitoringService class starts schedulers for each website.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebsiteMonitoringService {
    private final WebsiteMonitorScheduler websiteMonitorScheduler;
    private final ConfigurationReader configurationReader;

    @SneakyThrows
    @PostConstruct
    public void postConstruct() {
        MonitorConfig monitorConfig = configurationReader.readConfiguration();
        log.debug("Websites to monitor: {}", monitorConfig.getWebsites());

        for (WebsiteConfig websiteConfig : monitorConfig.getWebsites()) {
            websiteMonitorScheduler.addJob(websiteConfig);
        }
        websiteMonitorScheduler.start();
    }
}
