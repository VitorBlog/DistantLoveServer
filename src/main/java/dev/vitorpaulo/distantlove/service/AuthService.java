package dev.vitorpaulo.distantlove.service;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.vitorpaulo.distantlove.domain.User;
import dev.vitorpaulo.distantlove.exception.UserAlreadyExistsException;
import dev.vitorpaulo.distantlove.exception.UserNotFoundException;
import dev.vitorpaulo.distantlove.mapper.GetUserResponseMapper;
import dev.vitorpaulo.distantlove.repository.UserRepository;
import dev.vitorpaulo.distantlove.request.AuthUserRequest;
import dev.vitorpaulo.distantlove.request.CreateUserRequest;
import dev.vitorpaulo.distantlove.request.RefreshTokenRequest;
import dev.vitorpaulo.distantlove.request.VerifyEmailRequest;
import dev.vitorpaulo.distantlove.response.AuthUserResponse;
import dev.vitorpaulo.distantlove.response.GetUserResponse;
import io.jsonwebtoken.JwtBuilder;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private static final Long JWT_EXPIRE = 24_600_000L;

    private final UserService userService;
    private final UserRepository userRepository;

    private final JwtBuilder jwtBuilder;
    private final PasswordEncoder passwordEncoder;

    public AuthUserResponse register(CreateUserRequest request) throws UserAlreadyExistsException {
        userService.verifyEmail(new VerifyEmailRequest(request.getEmail()));

        final var user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .pronoun(request.getPronoun())
            .code(RandomStringUtils.randomNumeric(6))
            .refreshToken(RandomStringUtils.randomAlphanumeric(15))
            .createdAt(new Date())
            .build();

        userRepository.save(user);
        return AuthUserResponse.builder()
            .token(
                jwtBuilder
                    .claim("id", user.getId())
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE))
                    .compact()
            )
            .refreshToken(user.getRefreshToken())
            .build();
    }

    public AuthUserResponse login(AuthUserRequest request) throws UserNotFoundException {
        final var user = userRepository.findByEmail(request.getEmail().toLowerCase()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }

        user.setRefreshToken(RandomStringUtils.randomAlphanumeric(15));
        userRepository.save(user);

        return new AuthUserResponse(
            jwtBuilder
                .claim("id", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE))
                .compact(),
            user.getRefreshToken()
        );
    }

    public AuthUserResponse refreshToken(RefreshTokenRequest request) throws UserNotFoundException {
        final var user = userRepository.findByRefreshToken(request.getRefreshToken()).orElseThrow(UserNotFoundException::new);

        user.setRefreshToken(RandomStringUtils.randomAlphanumeric(15));
        userRepository.save(user);

        return new AuthUserResponse(
            jwtBuilder
                .claim("id", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE))
                .compact(),
            user.getRefreshToken()
        );
    }
}