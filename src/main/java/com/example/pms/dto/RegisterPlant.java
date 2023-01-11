package com.example.pms.dto;

import com.example.pms.plantProperties.FertilizerType;
import com.example.pms.plantProperties.FlowerPot;
import com.example.pms.plantProperties.SoilType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPlant {

    private Long Id;
    @NotEmpty(message = "Plant name cannot be empty!")
    private String plantName;
    @NotNull(message = "Choose watering timestamp!")
    private int wateringTimestampInDays;
    @NotNull
    private int sunExposureTimeStampInDays;
    @NotNull
    private int harvestingSeedingTimestampInDays;
    @NotNull
    private int pruningTimestampInDays;
    @NotNull
    private int cleaningLeavesTimestampInDays;
    private String soilTypeName;
    private String fertilizerTypeName;
    private String flowerPotName;
}
