package com.dans.authorization.application.port.in;

import com.dans.authorization.application.domain.model.User;

public interface RegisterUseCase {

    User register(RegisterCommand registerCommand);

}
