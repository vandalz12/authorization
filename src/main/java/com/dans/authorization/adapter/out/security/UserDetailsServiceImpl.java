package com.dans.authorization.adapter.out.security;

import com.dans.authorization.application.domain.model.User;
import com.dans.authorization.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoadUserPort loadUserPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loadUserPort.loadUserByUsername(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("User is not found.");
        }
        return new CustomUserDetails(user);
    }

}
