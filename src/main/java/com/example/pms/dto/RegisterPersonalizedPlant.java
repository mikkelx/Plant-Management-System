package com.example.pms.dto;

import com.example.pms.plant.Plant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPersonalizedPlant {
    private Long Id;
    @NotEmpty(message = "Plant label cannot be empty!")
    private String userLabel;
    @Pattern(regexp = "^[^0]+$", message="Plant must be choosen!")
    private String plantName;
    @NotNull(message = "Choose last watering! If not performed choose 0")
    private int lastWatering;
    private int lastSunExposure;
    private int lastHarvestingSeeding;
    private int lastPruning;
    private int lastCleaningLeaves;
    private int lastFertilizing;
    private int lastPotReplacement;
    private int lastSoilReplacement;


}
