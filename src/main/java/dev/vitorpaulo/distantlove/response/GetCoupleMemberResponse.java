package dev.vitorpaulo.distantlove.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class GetCoupleMemberResponse implements Serializable {

    private final Long id;
    private final GetUserResponse user;
}
