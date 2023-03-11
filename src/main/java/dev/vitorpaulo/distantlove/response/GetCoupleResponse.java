package dev.vitorpaulo.distantlove.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class GetCoupleResponse implements Serializable {

    private final Long id;
    private final Date anniversary;
    private final Date createdAt;
    private final GetCoupleMemberResponse partner;
}
