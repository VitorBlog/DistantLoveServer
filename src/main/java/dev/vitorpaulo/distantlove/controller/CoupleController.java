package dev.vitorpaulo.distantlove.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vitorpaulo.distantlove.exception.UserAlreadyHasCoupleException;
import dev.vitorpaulo.distantlove.exception.UserNotFoundException;
import dev.vitorpaulo.distantlove.model.DetailedUser;
import dev.vitorpaulo.distantlove.service.CoupleService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/couple")
public class CoupleController {

    private final CoupleService coupleService;

    @PostMapping("/{code}")
    public void create(@AuthenticationPrincipal DetailedUser user, @PathVariable String code)
        throws UserNotFoundException, UserAlreadyHasCoupleException {
        coupleService.create(user.getUser(), code);
    }
}
