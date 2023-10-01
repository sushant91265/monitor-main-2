//package com.site.monitor.service;
//
//import com.site.monitor.schedulers.WebsiteMonitoringJob;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.quartz.JobDataMap;
//import org.quartz.JobExecutionContext;
//import static org.mockito.Mockito.when;
//
//class WebsiteMonitorJobTest {
//    @Mock
//    private JobExecutionContext context;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void executeJobSuccessfully() {
//        String url = "http://example.com";
//        String contentRequirement = "Example Domain";
//
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put("url", url);
//        jobDataMap.put("contentRequirement", contentRequirement);
//
//        when(context.getMergedJobDataMap()).thenReturn(jobDataMap);
//
//        WebsiteMonitoringJob websiteMonitorJob = new WebsiteMonitoringJob();
//
//        websiteMonitorJob.execute(context);
//
//        assert(context.getMergedJobDataMap().getString("url").equals(url));
//        assert(context.getMergedJobDataMap().getString("contentRequirement").equals(contentRequirement));
//    }
//}