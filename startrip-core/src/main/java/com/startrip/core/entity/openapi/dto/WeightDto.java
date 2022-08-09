package com.startrip.core.entity.openapi.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeightDto {

    private int popWeight; // 100
    private int skyWeight; // 100
    private int airWeight; // 50
    private int sunRayWeight;  // 10

    public int totalWeightSum(){
        return popWeight + skyWeight + airWeight + skyWeight;
    }
}
