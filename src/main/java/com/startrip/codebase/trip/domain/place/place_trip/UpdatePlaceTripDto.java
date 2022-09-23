package com.startrip.codebase.trip.domain.place.place_trip;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class UpdatePlaceTripDto {
    private String userPartner;
    private UUID placeId;
    private Date startTime;
    private Date endTime;
    private Integer state;
    private String transportation;
    private String title;
}
