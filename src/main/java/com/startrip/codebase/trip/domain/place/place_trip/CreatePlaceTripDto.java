package com.startrip.codebase.trip.domain.place.place_trip;

import com.startrip.codebase.member.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreatePlaceTripDto {
    private UUID tripId;
    private User userId;
    private String userPartner;
    private UUID placeId;
    private Date startTime;
    private Date endTime;
    private Integer state;
    private String transportation;
    private String title;
}
