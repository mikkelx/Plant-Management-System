package com.example.pms.dto;

import com.example.pms.plant.Plant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
//    @Pattern(regexp = "^[^0]+$", message = "Choose last watering! If not performed choose 0")
    @NotNull(message = "Choose last watering! If not performed choose 0")
    private int lastWatering;
//    @Pattern(regexp = "^[^0]+$", message = "Choose last fertilizing! If not performed choose 0")
    private int lastFertilizing;
//    @Pattern(regexp = "^[^0]+$", message = "Choose last pot replacement! If not performed choose 0")
    private int lastPotReplacement;
//    @Pattern(regexp = "^[^0]+$", message = "Choose last soil replacement! If not performed choose 0")
    private int lastSoilReplacement;


}
