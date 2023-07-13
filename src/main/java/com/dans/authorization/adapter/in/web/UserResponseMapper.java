package com.dans.authorization.adapter.in.web;

import com.dans.authorization.application.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserResponseMapper {

    public UserResponse mapUserToUserResponse(User user) {
        if(Objects.isNull(user)) {
            return null;
        }
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

}
