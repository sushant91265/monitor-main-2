package com.site.monitor.service.interfaces;
import com.site.monitor.model.MonitorConfig;

import java.io.IOException;

public interface ConfigurationReader {
    MonitorConfig readConfiguration() throws IOException;
}
