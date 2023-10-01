package com.site.monitor.service.interfaces;

import com.site.monitor.model.RestApiResponse;

public interface HttpService {
     RestApiResponse makeGetCall(String url);
}
