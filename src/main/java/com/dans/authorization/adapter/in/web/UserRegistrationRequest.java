package com.dans.authorization.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString
public class UserRegistrationRequest {

    private String username;
    private String password;

}
