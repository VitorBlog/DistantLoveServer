package dev.vitorpaulo.distantlove.controller;

import dev.vitorpaulo.distantlove.exception.InvalidUserCodeException;
import dev.vitorpaulo.distantlove.exception.UserDoesntHasCoupleException;
import dev.vitorpaulo.distantlove.response.GetCoupleResponse;
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
            throws UserNotFoundException, UserAlreadyHasCoupleException, InvalidUserCodeException {
        coupleService.create(user.getUser(), code);
    }

    @GetMapping
    public GetCoupleResponse getSelfCouple(@AuthenticationPrincipal DetailedUser user) throws UserDoesntHasCoupleException {
        return coupleService.getSelfCouple(user.getUser());
    }
}
