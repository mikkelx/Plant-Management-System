package com.example.pms.dto;

import com.example.pms.plant.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class PersonalizedPlantDto {
    private Long Id;
    private String userLabel;
    private Date lastWatering;
    private Date lastFertilizing;
    private Date lastPotReplacement;
    private Plant plant;
}
