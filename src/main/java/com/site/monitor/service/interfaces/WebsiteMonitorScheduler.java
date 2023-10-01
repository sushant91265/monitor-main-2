package com.site.monitor.service.interfaces;

import com.site.monitor.model.WebsiteConfig;
import org.quartz.SchedulerException;

public interface WebsiteMonitorScheduler {
    boolean start() throws SchedulerException;
    boolean stop();
    boolean addJob(WebsiteConfig websiteConfig) throws SchedulerException;
}
