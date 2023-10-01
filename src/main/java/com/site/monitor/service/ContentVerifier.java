package com.site.monitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContentVerifier {

    public boolean isContentVerified(String response, String contentRequirement) {
        return response!=null && response.contains(contentRequirement);
    }
}
