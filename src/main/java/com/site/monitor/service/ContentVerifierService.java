package com.site.monitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContentVerifierService {

    public boolean isContentVerified(String response, String contentRequirement) {
        boolean isContentVerified = response != null && !response.isEmpty() && contentRequirement != null
                && !contentRequirement.isEmpty() && response.contains(contentRequirement);
        log.debug(isContentVerified ? "Content verified!!" : "Content not verified!!");
        return isContentVerified;
    }
}
