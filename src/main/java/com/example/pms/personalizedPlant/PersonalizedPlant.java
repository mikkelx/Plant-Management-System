package com.example.pms.personalizedPlant;

import com.example.pms.plant.Plant;
import com.example.pms.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PersonalizedPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalizedPlantId;
    private String userLabel;
    private LocalDate lastWatering;
    private LocalDate lastSunExposure;
    private LocalDate lastHarvestingSeeding;
    private LocalDate lastPruning;
    private LocalDate lastCleaningLeaves;

    private LocalDate lastFertilizing;
    private LocalDate lastPotReplacement;
    private LocalDate lastSoilReplacement;
    private String message;
    private boolean wateringNotificationSent;
    private boolean pruningNotificationSent;
    private boolean sunExposureNotificationSent;
    private boolean harvestingSeedingNotificationSent;
    private boolean cleaningLeavesNotificationSent;
    private boolean fertilizerNotificationSent;
    private boolean potNotificationSent;
    private boolean soilNotificationSent;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    @JoinColumn(name = "plantId")
    private Plant plant;

//    public PersonalizedPlant() {
//        this.user = user;
//        this.plant = plant;
//        this.userLabel = userLabel;
//        this.lastWatering = lastWatering;
//    }


    public PersonalizedPlant(User user, Plant plant, String userLabel, LocalDate lastWatering,
                             LocalDate lastFertilizing, LocalDate lastPotReplacement, LocalDate lastSoilReplacement) {
        this.userLabel = userLabel;
        this.lastWatering = lastWatering;
        this.lastFertilizing = lastFertilizing;
        this.user = user;
        this.plant = plant;
        this.lastPotReplacement = lastPotReplacement;
        this.lastSoilReplacement = lastSoilReplacement;
        this.message = "";
        this.wateringNotificationSent = false;
        this.sunExposureNotificationSent = false;
        this.harvestingSeedingNotificationSent = false;
        this.pruningNotificationSent = false;
        this.cleaningLeavesNotificationSent = false;
        this.fertilizerNotificationSent = false;
        this.potNotificationSent = false;
        this.soilNotificationSent = false;
    }

    public PersonalizedPlant(User user, Plant plant, String userLabel) {
        this.user = user;
        this.plant = plant;
        this.userLabel = userLabel;
        this.lastWatering = LocalDate.now();
        this.lastSunExposure = LocalDate.now();
        this.lastHarvestingSeeding = LocalDate.now();
        this.lastPruning = LocalDate.now();
        this.lastCleaningLeaves = LocalDate.now();
        this.lastFertilizing = LocalDate.now();
        this.lastPotReplacement = LocalDate.now();
        this.lastSoilReplacement = LocalDate.now();
        this.message = "";
        this.wateringNotificationSent = false;
        this.sunExposureNotificationSent = false;
        this.harvestingSeedingNotificationSent = false;
        this.pruningNotificationSent = false;
        this.cleaningLeavesNotificationSent = false;
        this.fertilizerNotificationSent = false;
        this.potNotificationSent = false;
        this.soilNotificationSent = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPlantName() {
        return this.plant.getPlantName();
    }

    public String checkActions() {

        if(this.needWatering()) {
            this.message += "Water me! ";
        }

        if(this.needSunExposure()) {
            this.message += "Expose me to sun! ";
        }

        if(this.needHarvesting()) {
            this.message += "Harvest me! ";
        }

        if(this.needPruning()) {
            this.message += "Prun me! ";
        }

        if(this.needCleaning()) {
            this.message += "Clean me! ";
        }

        if(this.needFertilizing()) {
            this.message += "Fertilize me! ";
        }

        if(this.needPotReplacement()) {
            this.message += "Replace my pot! ";
        }

        if(this.needSoilReplacement()) {
            this.message += "Replace my soil! ";
        }

        return this.message;
    }

    public boolean needWatering() {
        LocalDate now = LocalDate.now();

        Long wateringTime = ChronoUnit.DAYS.between(this.lastWatering, now);
        if(plant.getWateringTimestampInDays() <= wateringTime) {
            return true;
        }
        return false;
    }

    public boolean needSunExposure() {
        LocalDate now = LocalDate.now();

        Long sunExposure = ChronoUnit.DAYS.between(this.lastSunExposure, now);
        if(plant.getSunExposureTimeStampInDays() <= sunExposure) {
            return true;
        }
        return false;
    }

    public boolean needHarvesting() {
        LocalDate now = LocalDate.now();

        Long harvestingTime = ChronoUnit.DAYS.between(this.lastHarvestingSeeding, now);
        if(plant.getHarvestingSeedingTimestampInDays() <= harvestingTime) {
            return true;
        }
        return false;
    }

    public boolean needPruning() {
        LocalDate now = LocalDate.now();

        Long pruningTime = ChronoUnit.DAYS.between(this.lastPruning, now);
        if(plant.getPruningTimestampInDays() <= pruningTime) {
            return true;
        }
        return false;
    }

    public boolean needCleaning() {
        LocalDate now = LocalDate.now();

        Long cleaningTime = ChronoUnit.DAYS.between(this.lastCleaningLeaves, now);
        if(plant.getCleaningLeavesTimestampInDays() <= cleaningTime) {
            return true;
        }
        return false;
    }

    public boolean needFertilizing() {
        LocalDate now = LocalDate.now();

        Long fertilizingTime = ChronoUnit.DAYS.between(this.lastFertilizing, now);
        if(plant.getFertilizerType().getFrequencyInDays() <= fertilizingTime) {
            return true;
        }
        return false;
    }

    public boolean needPotReplacement() {
        LocalDate now = LocalDate.now();

        Long potTime = ChronoUnit.DAYS.between(this.lastPotReplacement, now);
        if(plant.getFlowerPot().getFreqOfReplacementInDays() <= potTime) {
            return true;
        }
        return false;
    }

    public boolean needSoilReplacement() {
        LocalDate now = LocalDate.now();

        Long soilTime = ChronoUnit.DAYS.between(this.lastSoilReplacement, now);
        if(plant.getSoilType().getFreqOfReplacementInDays() <= soilTime) {
            return true;
        }
        return false;
    }

    public boolean isWateringNotificationSent() {
        return wateringNotificationSent;
    }

    public boolean isFertilizerNotificationSent() {
        return fertilizerNotificationSent;
    }

    public boolean isPotNotificationSent() {
        return potNotificationSent;
    }

    @Override
    public String toString() {
        return "Id: " + personalizedPlantId + " - Label: " + userLabel + this.plant.toString();
    }
}
