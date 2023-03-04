package dev.vitorpaulo.distantlove.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import dev.vitorpaulo.distantlove.exception.UserAlreadyExistsException;
import dev.vitorpaulo.distantlove.model.DetailedUser;
import dev.vitorpaulo.distantlove.request.VerifyEmailRequest;
import dev.vitorpaulo.distantlove.response.GetUserResponse;
import dev.vitorpaulo.distantlove.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public GetUserResponse getSelfUser(@AuthenticationPrincipal DetailedUser user) {
        return userService.getSelfUser(user.getUser());
    }

    @PostMapping("/email")
    public void verifyEmail(@RequestBody VerifyEmailRequest request) throws UserAlreadyExistsException {
        userService.verifyEmail(request);
    }

    @PutMapping("/photo")
    public void updatePhoto(@AuthenticationPrincipal DetailedUser user, @RequestParam MultipartFile photo) throws IOException {
        userService.updatePhoto(user.getUser(), photo);
    }
}
