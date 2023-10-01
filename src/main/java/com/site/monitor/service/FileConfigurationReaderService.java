package com.site.monitor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.monitor.model.MonitorConfig;
import com.site.monitor.service.interfaces.ConfigurationReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * ConfigurationReaderService class is used to read the configuration file.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileConfigurationReaderService implements ConfigurationReader {

    private final static String CONFIG_FILE_PATH = "src/main/resources/config.json";
    private final ObjectMapper objectMapper;

    @Override
    public MonitorConfig readConfiguration() throws IOException  {
        MonitorConfig monitorConfig = objectMapper.readValue(new File(CONFIG_FILE_PATH), MonitorConfig.class);
        if(monitorConfig == null) {
            throw new RuntimeException("Error while reading configuration");
        }
        monitorConfig.getWebsites().forEach(config -> {
            log.info("Checking config for website {}", monitorConfig.getDefaultCheckIntervalSeconds());
            if(config.getCheckIntervalSeconds() <= 0){
                config.setCheckIntervalSeconds(monitorConfig.getDefaultCheckIntervalSeconds());
            }
        });

        return monitorConfig;
    }
}