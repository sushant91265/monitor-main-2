package com.site.monitor.schedulers;

import com.site.monitor.http.RestHttpService;
import com.site.monitor.logger.WebsiteResponseLogger;
import com.site.monitor.model.RestApiResponse;
import com.site.monitor.service.ContentVerifierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import static org.mockito.Mockito.*;

public class WebsiteMonitoringJobTest {

    private WebsiteMonitoringJob websiteMonitoringJob;
    @Mock private RestHttpService httpService;
    @Mock private ContentVerifierService contentVerifierService;
    @Mock private WebsiteResponseLogger websiteResponseLogger;
    @Mock private JobExecutionContext jobExecutionContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        websiteMonitoringJob = new WebsiteMonitoringJob();
        websiteMonitoringJob.setHttpService(httpService);
        websiteMonitoringJob.setContentVerifierService(contentVerifierService);
        websiteMonitoringJob.setWebsiteResponseLogger(websiteResponseLogger);
    }

    @Test
    public void testExecuteWebsiteUpAndContentVerified() {
        String url = "http://example.com";
        String contentRequirement = "Example Content";
        RestApiResponse mockResponse = new RestApiResponse(url,"",200,"Example Content",2);

        JobDataMap jobDataMap = mock(JobDataMap.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobExecutionContext.getMergedJobDataMap().getString("url")).thenReturn(url);
        when(jobExecutionContext.getMergedJobDataMap().getString("contentRequirement")).thenReturn(contentRequirement);
        when(httpService.makeGetCall(url)).thenReturn(mockResponse);
        when(contentVerifierService.isContentVerified(mockResponse.getData(), contentRequirement)).thenReturn(true);

        websiteMonitoringJob.execute(jobExecutionContext);

        verify(websiteResponseLogger).log(mockResponse, true, true);
    }

    @Test
    public void testExecuteWebsiteDown() {
        String url = "http://example.com";
        String contentRequirement = "Example Content";
        RestApiResponse mockResponse = new RestApiResponse(url,"",404,"Not Found",2);

        JobDataMap jobDataMap = mock(JobDataMap.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobExecutionContext.getMergedJobDataMap().getString("url")).thenReturn(url);
        when(jobExecutionContext.getMergedJobDataMap().getString("contentRequirement")).thenReturn(contentRequirement);
        when(httpService.makeGetCall(url)).thenReturn(mockResponse);

        websiteMonitoringJob.execute(jobExecutionContext);

        verify(websiteResponseLogger).log(mockResponse, false, false);
    }

    @Test
    public void testExecuteContentNotVerified() {
        String url = "http://example.com";
        String contentRequirement = "Example Content";
        RestApiResponse mockResponse = new RestApiResponse(url,"",200,"Different Content",2);

        JobDataMap jobDataMap = mock(JobDataMap.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobExecutionContext.getMergedJobDataMap().getString("url")).thenReturn(url);
        when(jobExecutionContext.getMergedJobDataMap().getString("contentRequirement")).thenReturn(contentRequirement);
        when(httpService.makeGetCall(url)).thenReturn(mockResponse);
        when(contentVerifierService.isContentVerified(mockResponse.getData(), contentRequirement)).thenReturn(false);

        websiteMonitoringJob.execute(jobExecutionContext);

        verify(websiteResponseLogger).log(mockResponse, false, true);
    }

    @Test
    public void testExecuteContentWithEmptyRequirement() {
        String url = "http://example.com";
        String contentRequirement = "";
        RestApiResponse mockResponse = new RestApiResponse(url,"",200,"Different Content",2);

        JobDataMap jobDataMap = mock(JobDataMap.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobExecutionContext.getMergedJobDataMap().getString("url")).thenReturn(url);
        when(jobExecutionContext.getMergedJobDataMap().getString("contentRequirement")).thenReturn(contentRequirement);
        when(httpService.makeGetCall(url)).thenReturn(mockResponse);
        when(contentVerifierService.isContentVerified(mockResponse.getData(), contentRequirement)).thenReturn(false);

        websiteMonitoringJob.execute(jobExecutionContext);

        verify(websiteResponseLogger).log(mockResponse, false, true);
    }

    @Test
    public void testExecuteContentWithNullRequirement() {
        String url = "http://example.com";
        String contentRequirement = null;
        RestApiResponse mockResponse = new RestApiResponse(url,"",200,"Different Content",2);

        JobDataMap jobDataMap = mock(JobDataMap.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobExecutionContext.getMergedJobDataMap().getString("url")).thenReturn(url);
        when(jobExecutionContext.getMergedJobDataMap().getString("contentRequirement")).thenReturn(contentRequirement);
        when(httpService.makeGetCall(url)).thenReturn(mockResponse);
        when(contentVerifierService.isContentVerified(mockResponse.getData(), contentRequirement)).thenReturn(false);

        websiteMonitoringJob.execute(jobExecutionContext);

        verify(websiteResponseLogger).log(mockResponse, false, true);
    }

    @Test
    public void testExecuteContentWithBothEmpty() {
        String url = "";
        String contentRequirement = "";
        RestApiResponse mockResponse = new RestApiResponse(url,"",200,"Different Content",2);

        JobDataMap jobDataMap = mock(JobDataMap.class);
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobExecutionContext.getMergedJobDataMap().getString("url")).thenReturn(url);
        when(jobExecutionContext.getMergedJobDataMap().getString("contentRequirement")).thenReturn(contentRequirement);
        when(httpService.makeGetCall(url)).thenReturn(mockResponse);
        when(contentVerifierService.isContentVerified(mockResponse.getData(), contentRequirement)).thenReturn(false);

        websiteMonitoringJob.execute(jobExecutionContext);

        verify(websiteResponseLogger).log(mockResponse, false, true);
    }
}
