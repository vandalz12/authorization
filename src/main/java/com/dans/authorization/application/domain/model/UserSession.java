package com.dans.authorization.application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserSession {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String scope;

}
