package com.dans.authorization.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Builder
@Jacksonized
@ToString
public class UserResponse {

    private UUID id;
    private String username;

}
