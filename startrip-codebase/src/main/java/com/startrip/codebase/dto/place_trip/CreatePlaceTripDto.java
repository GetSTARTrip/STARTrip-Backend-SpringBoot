package com.startrip.codebase.dto.place_trip;

import com.startrip.codebase.domain.state.State;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreatePlaceTripDto {
    private UUID tripId;
    private Long userId;
    private String userPartner;
    private UUID placeId;
    private Date startTime;
    private Date endTime;
    private State state;
    private String transportation;
    private String title;

//    public String placeTripState() {
//        Date time = new Date();
//        String st = null;
//        if(startTime.after(endTime)) st = "잘못된 시간";
//        else if(startTime.after(time)) st = "여행 계획";
//        else if(startTime.before(time) && endTime.after(time)) st = "여행 상태";
//        else if(endTime.before(time)) st = "여행 종료";
//        return st;
//    }
}
