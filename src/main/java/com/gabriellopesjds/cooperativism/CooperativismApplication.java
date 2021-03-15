package com.gabriellopesjds.cooperativism;

import com.gabriellopesjds.cooperativism.config.property.AssociatedIntegrationServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AssociatedIntegrationServerProperties.class)
public class CooperativismApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooperativismApplication.class, args);
    }

}
