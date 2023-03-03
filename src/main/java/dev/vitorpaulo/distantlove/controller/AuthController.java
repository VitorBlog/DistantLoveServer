package dev.vitorpaulo.distantlove.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vitorpaulo.distantlove.exception.UserAlreadyExistsException;
import dev.vitorpaulo.distantlove.exception.UserNotFoundException;
import dev.vitorpaulo.distantlove.request.AuthUserRequest;
import dev.vitorpaulo.distantlove.request.CreateUserRequest;
import dev.vitorpaulo.distantlove.request.RefreshTokenRequest;
import dev.vitorpaulo.distantlove.request.VerifyEmailRequest;
import dev.vitorpaulo.distantlove.response.AuthUserResponse;
import dev.vitorpaulo.distantlove.response.GetUserResponse;
import dev.vitorpaulo.distantlove.service.AuthService;
import dev.vitorpaulo.distantlove.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthUserResponse register(@Valid @RequestBody CreateUserRequest request) throws UserAlreadyExistsException {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthUserResponse login(@Valid @RequestBody AuthUserRequest request) throws UserNotFoundException {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthUserResponse refresh(@Valid @RequestBody RefreshTokenRequest request) throws UserNotFoundException {
        return authService.refreshToken(request);
    }
}
