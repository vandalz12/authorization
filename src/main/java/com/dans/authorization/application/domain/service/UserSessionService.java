package com.dans.authorization.application.domain.service;

import com.dans.authorization.application.domain.model.User;
import com.dans.authorization.application.port.in.*;
import com.dans.authorization.application.port.out.LoadUserPort;
import com.dans.authorization.application.port.out.RegisterUserPort;
import com.dans.authorization.common.PasswordUtil;
import com.dans.authorization.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSessionService implements RegisterUseCase {

    private final PasswordUtil passwordUtil;
    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;

    @Override
    @Transactional
    public User register(RegisterCommand registerCommand) {
        User existingUser = loadUserPort.loadUserByUsername(registerCommand.getUsername());
        if(Objects.nonNull(existingUser)) {
            throw new UserAlreadyExistsException("User already exists.");
        }
        String salt = passwordUtil.getSalt();
        User user = User
                .builder()
                .id(UUID.randomUUID())
                .username(registerCommand.getUsername())
                .salt(salt)
                .password(passwordUtil.encode(registerCommand.getPassword(), salt))
                .build();
        return registerUserPort.register(user);
    }

}
