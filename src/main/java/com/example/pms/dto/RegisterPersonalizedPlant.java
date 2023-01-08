package com.example.pms.dto;

import com.example.pms.plant.Plant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPersonalizedPlant {
    private Long Id;
    private String userLabel;
    private Integer lastWatering;
    private Integer lastFertilizing;
    private Integer lastPotReplacement;
    private String plantName;
}
