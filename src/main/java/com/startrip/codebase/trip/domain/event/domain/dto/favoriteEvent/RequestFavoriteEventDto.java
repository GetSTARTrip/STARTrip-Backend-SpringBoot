package com.startrip.codebase.trip.domain.event.domain.dto.favoriteEvent;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RequestFavoriteEventDto {
    private UUID eventId;
}
