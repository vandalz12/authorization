package com.dans.authorization.configuration;

import com.dans.authorization.configuration.properties.KeyPairsConfigurationProperties;
import com.dans.authorization.configuration.properties.OAuth2ConfigurationProperties;
import com.dans.authorization.exception.KeyGeneratorException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.*;
import java.net.URISyntaxException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfiguration {

    private final KeyPairsConfigurationProperties keyPairsConfigurationProperties;
    private final OAuth2ConfigurationProperties oAuth2ConfigurationProperties;

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate, TokenSettings tokenSettings) {
        RegisteredClient oidcClient = RegisteredClient.withId(oAuth2ConfigurationProperties.getRegisteredClientId())
                .clientId(oAuth2ConfigurationProperties.getClientId())
                .clientSecret(oAuth2ConfigurationProperties.getClientSecretAlgorithm() + oAuth2ConfigurationProperties.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8080/authorized")
                .scopes(scopes -> scopes.addAll(oAuth2ConfigurationProperties.getScopes()))
                .tokenSettings(tokenSettings)
                .build();

        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        jdbcRegisteredClientRepository.save(oidcClient);
        return jdbcRegisteredClientRepository;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private KeyPair generateRsaKey() {
        return new KeyPair(getPublicKey(), getPrivateKey());
    }

    private PrivateKey getPrivateKey() {
        try {
            File file = new File(this.getClass().getClassLoader().getResource(keyPairsConfigurationProperties.getPrivateKey()).toURI());
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            byte[] keyBytes = new byte[(int) file.length()];
            dataInputStream.readFully(keyBytes);
            dataInputStream.close();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(keyPairsConfigurationProperties.getAlgorithm());
            return keyFactory.generatePrivate(spec);
        } catch (URISyntaxException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new KeyGeneratorException(e.getMessage(), e);
        }
    }

    private PublicKey getPublicKey() {
        try {
            File file = new File(this.getClass().getClassLoader().getResource(keyPairsConfigurationProperties.getPublicKey()).toURI());
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            byte[] keyBytes = new byte[(int) file.length()];
            dataInputStream.readFully(keyBytes);
            dataInputStream.close();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(keyPairsConfigurationProperties.getAlgorithm());
            return keyFactory.generatePublic(spec);
        } catch (URISyntaxException | IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new KeyGeneratorException(e.getMessage(), e);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings
                .builder()
                .build();
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings
                .builder()
                .accessTokenTimeToLive(Duration.ofMinutes(15L))
                .refreshTokenTimeToLive(Duration.ofHours(1L))
                .build();
    }

}
