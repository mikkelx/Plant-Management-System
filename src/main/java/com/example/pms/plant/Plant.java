package com.example.pms.plant;

import com.example.pms.plantProperties.FertilizerType;
import com.example.pms.plantProperties.FlowerPot;
import com.example.pms.plantProperties.SoilType;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plantId;
    @NotNull
    private String plantName;
    @NotNull
    private int wateringTimestampInDays;
    @NotNull
    private int sunExposureTimeStampInDays;
    @NotNull
    private int harvestingSeedingTimestampInDays;
    @NotNull
    private int pruningTimestampInDays;
    @NotNull
    private int cleaningLeavesTimestampInDays;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "soilId")
    private SoilType soilType;

    @ManyToOne
    @JoinColumn(name = "fertilizerId")
    private FertilizerType fertilizerType;

    @ManyToOne
    @JoinColumn(name = "potId")
    private FlowerPot flowerPot;


    public Plant( String plantName, int wateringTimestampInDays, int sunExposureTimeStampInDays,int harvestingSeedingTimestampInDays,
                  int pruningTimestampInDays, int cleaningLeavesTimestampInDays, SoilType soilType,
                  FertilizerType fertilizerType, FlowerPot flowerPot) {
        this.plantName = plantName;
        this.wateringTimestampInDays = wateringTimestampInDays;
        this.sunExposureTimeStampInDays = sunExposureTimeStampInDays;
        this.harvestingSeedingTimestampInDays = harvestingSeedingTimestampInDays;
        this.pruningTimestampInDays = pruningTimestampInDays;
        this.cleaningLeavesTimestampInDays = cleaningLeavesTimestampInDays;
        this.soilType = soilType;
        this.fertilizerType = fertilizerType;
        this.flowerPot = flowerPot;
    }

    @Override
    public String toString() {
        return this.plantName;// + " " + soilType.toString();
    }

    public String getPlantName() {
        return plantName;
    }
}
