package com.site.monitor.schedulers;

import com.site.monitor.model.WebsiteConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuartzWebsiteMonitoringSchedulerTest {

    private QuartzWebsiteMonitoringScheduler websiteMonitoringScheduler;

    @Mock
    private SchedulerFactoryBean schedulerFactory;

    @Mock
    private Scheduler scheduler;

    @BeforeEach
    void setUp() throws SchedulerException {
        MockitoAnnotations.openMocks(this);
        when(schedulerFactory.getScheduler()).thenReturn(scheduler);
        websiteMonitoringScheduler = new QuartzWebsiteMonitoringScheduler(schedulerFactory);
    }

    @Test
    void addJob_Success() throws SchedulerException {
        WebsiteConfig websiteConfig = new WebsiteConfig();
        websiteConfig.setId(1L);
        websiteConfig.setUrl("https://example.com");
        websiteConfig.setCheckIntervalSeconds(60);
        websiteConfig.setContentRequirement("Required Content");

        boolean result = websiteMonitoringScheduler.addJob(websiteConfig);

        assertTrue(result);
    }

    @Test
    void addJob_AlreadyRunning() {
        websiteMonitoringScheduler.isRunning.set(true);

        WebsiteConfig websiteConfig = new WebsiteConfig();
        websiteConfig.setId(1L);

        assertThrows(RuntimeException.class, () -> websiteMonitoringScheduler.addJob(websiteConfig));
    }

    @Test
    void start_Success() throws SchedulerException {
        when(scheduler.isStarted()).thenReturn(false);

        boolean result = websiteMonitoringScheduler.start();

        assertTrue(result);
        verify(scheduler).start();
    }

    @Test
    void start_AlreadyRunning() throws SchedulerException {
        websiteMonitoringScheduler.isRunning.set(true);

        boolean result = websiteMonitoringScheduler.start();

        assertFalse(result);
        verify(scheduler, never()).start();
    }

    @Test
    void stop_Success() throws SchedulerException {
        when(scheduler.isStarted()).thenReturn(false);

        doNothing().when(scheduler).start();

        websiteMonitoringScheduler.start();

        boolean result = websiteMonitoringScheduler.stop();

        assertTrue(result);
        verify(scheduler).shutdown();
    }

    @Test
    void stop_NotRunning() throws SchedulerException {
        when(scheduler.isStarted()).thenReturn(false);

        boolean result = websiteMonitoringScheduler.stop();

        assertFalse(result);
        verify(scheduler, never()).shutdown();
    }
}