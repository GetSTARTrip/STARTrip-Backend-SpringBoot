package com.startrip.core.util.datatest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DataTestDto {
    private UUID testId;
    private String placeName;
}
