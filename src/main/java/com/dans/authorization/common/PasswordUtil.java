package com.dans.authorization.common;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtil {

    private final PasswordEncoder passwordEncoder;

    public String encode(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    public boolean match(PasswordChallenge passwordChallenge) {
        return passwordEncoder.matches(passwordChallenge.getInputPassword() + passwordChallenge.getSalt(), passwordChallenge.getPassword());
    }

    public String getSalt() {
        return BCrypt.gensalt();
    }

    @Getter
    @Builder
    @ToString
    public static class PasswordChallenge {

        private String inputPassword;
        private String password;
        private String salt;

    }

}
