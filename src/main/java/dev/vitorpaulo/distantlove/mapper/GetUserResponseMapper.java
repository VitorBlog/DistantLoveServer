package dev.vitorpaulo.distantlove.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import dev.vitorpaulo.distantlove.domain.User;
import dev.vitorpaulo.distantlove.model.BucketFolder;
import dev.vitorpaulo.distantlove.response.GetUserResponse;
import dev.vitorpaulo.distantlove.service.MinioService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GetUserResponseMapper implements Function<User, GetUserResponse> {

    private final MinioService minioService;

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .avatar(minioService.getSignedPhoto(BucketFolder.USER_AVATAR, user.getAvatar()))
            .pronoun(user.getPronoun())
            .code(user.getCode())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
