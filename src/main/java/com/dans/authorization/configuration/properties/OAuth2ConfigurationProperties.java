package com.dans.authorization.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2ConfigurationProperties {

    private String baseUrlAuthorizationServer;
    private String registeredClientId;
    private String clientId;
    private String clientSecret;
    private String clientSecretAlgorithm;
    private Set<String> scopes;

}
