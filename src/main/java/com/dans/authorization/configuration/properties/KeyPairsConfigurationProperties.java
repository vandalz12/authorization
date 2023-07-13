package com.dans.authorization.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.keys")
public class KeyPairsConfigurationProperties {

    private String algorithm;
    private String privateKey;
    private String publicKey;

}
