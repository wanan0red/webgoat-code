package com.wanan.webgoat.container.asciidoc;

import org.springframework.core.env.Environment;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentExposure implements ApplicationContextAware {
    private static ApplicationContext context;
    public static Environment getEnv(){
        return context.getEnvironment();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
