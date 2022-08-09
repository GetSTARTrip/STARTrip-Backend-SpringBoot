package com.startrip.core.entity.event_trip.dto;

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
