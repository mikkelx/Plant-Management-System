package com.example.pms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlantDto {
    private Long Id;
    private String plantName;
    private int wateringTimestampInDays;
    private String soil;
    private String fertilizer;
    private String pot;
}
