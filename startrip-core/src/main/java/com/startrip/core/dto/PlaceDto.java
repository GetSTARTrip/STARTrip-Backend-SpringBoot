package com.startrip.core.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlaceDto {

    @NotNull
    private String placeName;

    @NotNull
    private String address;

    private UUID categoryId;
    private String placeDescription;
    private String placePhoto;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private List<String>placeAnotherName;

}