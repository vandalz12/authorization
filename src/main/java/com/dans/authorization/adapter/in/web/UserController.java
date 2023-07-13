package com.dans.authorization.adapter.in.web;

import com.dans.authorization.application.domain.model.User;
import com.dans.authorization.application.port.in.*;
import com.dans.authorization.constant.ResourcePath;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserResponseMapper userResponseMapper;
    private final RegisterUseCase registerUseCase;

    @PostMapping(value = ResourcePath.REGISTER_PATH, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> register(
        @RequestBody UserRegistrationRequest userRegistrationRequest,
        HttpServletRequest request
    ) {
        RegisterCommand registerCommand = RegisterCommand
                .builder()
                .username(userRegistrationRequest.getUsername())
                .password(userRegistrationRequest.getPassword())
                .build();
        User user = registerUseCase.register(registerCommand);
        UserResponse userResponse = userResponseMapper.mapUserToUserResponse(user);
        URI location = UriComponentsBuilder.fromUriString(request.getRequestURL().toString()).path("/{id}").buildAndExpand(userResponse.getId()).toUri();
        return ResponseEntity.created(location).body(userResponse);
    }

}
