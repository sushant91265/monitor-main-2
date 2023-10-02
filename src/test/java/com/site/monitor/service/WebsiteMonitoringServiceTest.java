package com.site.monitor.service;

import com.site.monitor.model.MonitorConfig;
import com.site.monitor.model.WebsiteConfig;
import com.site.monitor.service.interfaces.ConfigurationReader;
import com.site.monitor.service.interfaces.WebsiteMonitorScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class WebsiteMonitoringServiceTest {

    private WebsiteMonitoringService websiteMonitoringService;

    @Mock
    private WebsiteMonitorScheduler websiteMonitorScheduler;

    @Mock
    private ConfigurationReader configurationReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        websiteMonitoringService = new WebsiteMonitoringService(websiteMonitorScheduler, configurationReader);
    }

    @Test
    public void testPostConstruct_Success() throws Exception {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setWebsites(Collections.singletonList(new WebsiteConfig()));

        when(configurationReader.readConfiguration()).thenReturn(monitorConfig);

        websiteMonitoringService.postConstruct();

        verify(websiteMonitorScheduler).addJob(any(WebsiteConfig.class));
        verify(websiteMonitorScheduler).start();
    }
}