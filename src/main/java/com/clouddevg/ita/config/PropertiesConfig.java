package com.clouddevg.ita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:custom.properties")
public class PropertiesConfig {

    private final Environment env;

    public PropertiesConfig(Environment env) {
        this.env = env;
    }

    public String getConfigValue(String configKey) {
        return env.getProperty(configKey);
    }
}
