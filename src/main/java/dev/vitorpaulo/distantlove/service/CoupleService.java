package dev.vitorpaulo.distantlove.service;

import java.util.Date;

import dev.vitorpaulo.distantlove.exception.InvalidUserCodeException;
import dev.vitorpaulo.distantlove.exception.UserDoesntHasCoupleException;
import dev.vitorpaulo.distantlove.mapper.GetUserResponseMapper;
import dev.vitorpaulo.distantlove.response.GetCoupleMemberResponse;
import dev.vitorpaulo.distantlove.response.GetCoupleResponse;
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

    private final GetUserResponseMapper userResponseMapper;

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

    public GetCoupleResponse getSelfCouple(User user) throws UserDoesntHasCoupleException {
        final var member = findMember(user);
        final var couple = member.getCouple();
        final var partner = findPartner(couple, user);

        return GetCoupleResponse.builder()
                .id(couple.getId())
                .anniversary(couple.getAnniversary())
                .createdAt(couple.getCreatedAt())
                .partner(
                        GetCoupleMemberResponse.builder()
                                .id(partner.getId())
                                .user(userResponseMapper.apply(partner.getUser()))
                                .build()
                )
                .build();
    }

    public CoupleMember findMember(User user) throws UserDoesntHasCoupleException {
        return memberRepository.findByUser(user).orElseThrow(UserDoesntHasCoupleException::new);
    }

    public CoupleMember findPartner(Couple couple, User user) throws UserDoesntHasCoupleException {
        return memberRepository.findByCoupleAndUserIsNot(couple, user).orElseThrow(UserDoesntHasCoupleException::new);
    }
}
