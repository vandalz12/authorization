package com.dans.authorization.application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class User {

    private UUID id;
    private String username;
    private String password;
    private String salt;

}
