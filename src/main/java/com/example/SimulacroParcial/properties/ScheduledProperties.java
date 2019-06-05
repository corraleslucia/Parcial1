package com.example.SimulacroParcial.properties;

import java.io.IOException;
import java.util.Properties;

public enum ScheduledProperties {

    SCHEDULED_PROPERTIES;

    private static final String FILE_NAME = "scheduled.properties";
    private static final String CONDITION_TIME = "condition.time";

    private Properties properties = new Properties();

    private String getProperty(String propertyName) {
        if (properties.isEmpty()) {
            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream(FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(propertyName);
    }

    public String getConditionTime() {
        return getProperty(CONDITION_TIME);
    }


}
