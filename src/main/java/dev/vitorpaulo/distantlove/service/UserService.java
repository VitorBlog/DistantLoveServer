package dev.vitorpaulo.distantlove.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.vitorpaulo.distantlove.domain.User;
import dev.vitorpaulo.distantlove.exception.EmailNotFoundException;
import dev.vitorpaulo.distantlove.exception.UserAlreadyExistsException;
import dev.vitorpaulo.distantlove.exception.UserNotFoundException;
import dev.vitorpaulo.distantlove.mapper.GetUserResponseMapper;
import dev.vitorpaulo.distantlove.model.DetailedUser;
import dev.vitorpaulo.distantlove.repository.UserRepository;
import dev.vitorpaulo.distantlove.request.VerifyEmailRequest;
import dev.vitorpaulo.distantlove.response.GetUserResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final GetUserResponseMapper userResponseMapper;
    private final UserRepository userRepository;

    public GetUserResponse getSelfUser(User user) {
        return userResponseMapper.apply(user);
    }

    public void verifyEmail(VerifyEmailRequest request) throws UserAlreadyExistsException {
        userRepository.findByEmail(request.getEmail())
            .orElseThrow(UserAlreadyExistsException::new);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new DetailedUser(
            userRepository.findByEmail(username.toLowerCase()).orElseThrow(EmailNotFoundException::new)
        );
    }
}