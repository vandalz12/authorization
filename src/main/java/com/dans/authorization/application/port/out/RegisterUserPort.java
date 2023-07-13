package com.dans.authorization.application.port.out;

import com.dans.authorization.application.domain.model.User;

public interface RegisterUserPort {

    User register(User user);

}
