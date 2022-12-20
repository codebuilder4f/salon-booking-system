package com.mpb.salon.bookig.system;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;
import java.util.Properties;

public class ResourceBundleUtil {
    public static String getYmlStringForActive(String propertyFileName, String propertyName) {
        YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
        Properties properties = yamlMapFactoryBean.getObject();
        yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource(propertyFileName  + ".yml"));
        properties = yamlMapFactoryBean.getObject();
        //Get parameters in yml
        String param = properties.getProperty(propertyName);
        if(Objects.equals(param, "")){
            return "0";
        }
        return param;
    }

}
