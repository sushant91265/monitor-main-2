package com.site.monitor.schedulers;

import com.site.monitor.model.WebsiteConfig;
import com.site.monitor.service.interfaces.WebsiteMonitorScheduler;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class QuartzWebsiteMonitoringScheduler implements WebsiteMonitorScheduler {

    private final SchedulerFactoryBean schedulerFactory;
    private Scheduler scheduler;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public QuartzWebsiteMonitoringScheduler(@Autowired SchedulerFactoryBean schedulerFactory) throws SchedulerException {
        this.schedulerFactory = schedulerFactory;
        this.scheduler = schedulerFactory.getScheduler();
    }

    @SneakyThrows
    @PreDestroy
    public void preDestroy() {
        this.stop();
    }


    @SneakyThrows
    @Override
    public boolean start() {
        if (!this.isRunning.get()) {
            log.info("Stating WebsiteMonitoringScheduler");
            this.scheduler.start();
            return true;
        }
        return false;
    }

    @SneakyThrows
    @Override
    public boolean stop() {
        if (this.isRunning.get()) {
            try {
                this.scheduler.shutdown();
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean addJob(WebsiteConfig websiteConfig) throws SchedulerException {
        if (this.isRunning.equals(new AtomicBoolean(true))) {
            throw new RuntimeException("Cannot add jobs to already running scheduler");
        }

        JobDetail jobDetail = getJobDetail(websiteConfig);
        SimpleTrigger simpleTrigger = getTrigger(websiteConfig, jobDetail);
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        return true;
    }

    private SimpleTrigger getTrigger(WebsiteConfig websiteConfig, JobDetail jobDetail) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity("Trigger" + websiteConfig.getId())
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(websiteConfig.getCheckIntervalSeconds())
                        .repeatForever())
                .build();
    }

    private JobDetail getJobDetail(WebsiteConfig websiteConfig) {
        return JobBuilder
                .newJob()
                .ofType(WebsiteMonitoringJob.class)
                .withIdentity("Job" + websiteConfig.getId())
                .usingJobData("url", websiteConfig.getUrl())
                .usingJobData("contentRequirement", websiteConfig.getContentRequirement())
                .storeDurably()
                .build();
    }
}
