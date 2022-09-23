package com.startrip.codebase.trip.domain.event.domain.dto.event_trip;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseEventTripDto {
    private UUID tripId;
    private UUID eventId;
    private String title;
}
