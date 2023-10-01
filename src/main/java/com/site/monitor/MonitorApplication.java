package com.site.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication
public class MonitorApplication {
	//TODO: scheduler not starting
	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}
}

