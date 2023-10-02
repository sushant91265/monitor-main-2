package com.site.monitor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.monitor.model.MonitorConfig;
import com.site.monitor.model.WebsiteConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class FileConfigurationReaderServiceTest {

    private FileConfigurationReaderService configurationReaderService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        configurationReaderService = new FileConfigurationReaderService(objectMapper);
    }

    @Test
    public void testReadConfiguration_Success() throws IOException {
        MonitorConfig expectedConfig = new MonitorConfig();
        expectedConfig.setDefaultCheckIntervalSeconds(60);
        expectedConfig.setWebsites(Collections.singletonList(new WebsiteConfig()));
        when(objectMapper.readValue(any(File.class), eq(MonitorConfig.class))).thenReturn(expectedConfig);
        MonitorConfig actualConfig = configurationReaderService.readConfiguration();
        assertEquals(expectedConfig, actualConfig);
    }

    @Test
    public void testReadConfiguration_IOException() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(MonitorConfig.class))).thenThrow(IOException.class);
        assertThrows(IOException.class, () -> configurationReaderService.readConfiguration());
    }

    @Test
    public void testReadConfiguration_NullConfig() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(MonitorConfig.class))).thenReturn(null);
        assertThrows(RuntimeException.class, () -> configurationReaderService.readConfiguration());
    }

    @Test
    public void testReadConfiguration_EmptyConfig() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(MonitorConfig.class))).thenReturn(new MonitorConfig());
        assertThrows(RuntimeException.class, () -> configurationReaderService.readConfiguration());
    }
}