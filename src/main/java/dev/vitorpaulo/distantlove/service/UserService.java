package dev.vitorpaulo.distantlove.service;

import dev.vitorpaulo.distantlove.model.BucketFolder;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final MinioService minioService;
    private final UserRepository userRepository;

    private final GetUserResponseMapper userResponseMapper;

    public GetUserResponse getSelfUser(User user) {
        return userResponseMapper.apply(user);
    }

    public void verifyEmail(VerifyEmailRequest request) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new DetailedUser(
            userRepository.findByEmail(username.toLowerCase()).orElseThrow(EmailNotFoundException::new)
        );
    }

    public void updatePhoto(User user, MultipartFile photo) throws IOException {
        user.setAvatar(
                minioService.uploadPhoto(BucketFolder.USER_AVATAR, photo.getInputStream(), "png")
        );
        userRepository.save(user);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByCode(String code) throws UserNotFoundException {
        return userRepository.findByCode(code).orElseThrow(UserNotFoundException::new);
    }
}
