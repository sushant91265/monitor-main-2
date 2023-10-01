package com.site.monitor.service.interfaces;

import java.util.Optional;

public interface HttpService {
    <T> Optional<T> makeGetCall(String url, Class<T> responseCls);
}
