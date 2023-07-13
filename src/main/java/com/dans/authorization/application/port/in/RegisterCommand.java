package com.dans.authorization.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RegisterCommand {

    private String username;
    private String password;

}
