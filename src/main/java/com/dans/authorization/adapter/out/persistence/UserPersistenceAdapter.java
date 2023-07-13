package com.dans.authorization.adapter.out.persistence;

import com.dans.authorization.application.domain.model.User;
import com.dans.authorization.application.port.out.LoadUserPort;
import com.dans.authorization.application.port.out.RegisterUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, RegisterUserPort {

    private final UserEntityMapper userEntityMapper;
    private final SpringDataJpaUserRepository springDataJpaUserRepository;

    @Override
    public User loadUserByUsername(String username) {
        return springDataJpaUserRepository
                .findByUsername(username)
                .map(userEntityMapper::mapUserEntityToUser)
                .orElse(null);
    }

    @Override
    public User register(User user) {
        UserEntity userEntity = new UserEntity(user.getId())
                .username(user.getUsername())
                .salt(user.getSalt())
                .password(user.getPassword());
        return userEntityMapper.mapUserEntityToUser(springDataJpaUserRepository.save(userEntity));
    }

}
