package com.site.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(schedulerJobFactory());
        return schedulerFactoryBean;
    }

    @Bean
    public SpringSchedulerJobFactory schedulerJobFactory() {
        return new SpringSchedulerJobFactory();
    }
}
