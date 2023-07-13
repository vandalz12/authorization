package com.dans.authorization.adapter.out.persistence;

import com.dans.authorization.application.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserEntityMapper {

    public User mapUserEntityToUser(UserEntity userEntity) {
        if(Objects.isNull(userEntity)) {
            return null;
        }
        return User
                .builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .salt(userEntity.getSalt())
                .password(userEntity.getPassword())
                .build();
    }

}
