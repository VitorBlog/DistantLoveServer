package dev.vitorpaulo.distantlove.service;

import java.util.Date;

import dev.vitorpaulo.distantlove.exception.InvalidUserCodeException;
import org.springframework.stereotype.Service;

import dev.vitorpaulo.distantlove.domain.Couple;
import dev.vitorpaulo.distantlove.domain.CoupleMember;
import dev.vitorpaulo.distantlove.domain.User;
import dev.vitorpaulo.distantlove.exception.UserAlreadyHasCoupleException;
import dev.vitorpaulo.distantlove.exception.UserNotFoundException;
import dev.vitorpaulo.distantlove.repository.CoupleMemberRepository;
import dev.vitorpaulo.distantlove.repository.CoupleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoupleService {

    private final UserService userService;

    private final CoupleRepository coupleRepository;
    private final CoupleMemberRepository memberRepository;

    public void create(User user, String code) throws UserNotFoundException, UserAlreadyHasCoupleException, InvalidUserCodeException {
        if (user.getCode().equals(code)) {
            throw new InvalidUserCodeException();
        }

        final var partner = userService.findUserByCode(code);

        if (memberRepository.existsByUser(user) || memberRepository.existsByUser(partner)) {
            throw new UserAlreadyHasCoupleException();
        }

        final var couple = Couple.builder()
            .createdAt(new Date())
            .build();
        coupleRepository.save(couple);

        memberRepository.save(CoupleMember.builder().couple(couple).user(user).build());
        memberRepository.save(CoupleMember.builder().couple(couple).user(partner).build());

        userService.removeCode(user);
        userService.removeCode(partner);
    }
}
