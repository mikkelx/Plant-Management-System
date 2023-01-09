package com.example.pms.personalizedPlant;

import com.example.pms.plant.Plant;
import com.example.pms.user.User;
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
    private LocalDate lastFertilizing;
    private LocalDate lastPotReplacement;
    private LocalDate lastSoilReplacement;
    private String message;
    private boolean wateringNotificationSent;
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
        this.fertilizerNotificationSent = false;
        this.potNotificationSent = false;
        this.soilNotificationSent = false;
    }

    public PersonalizedPlant(User user, Plant plant, String userLabel) {
        this.user = user;
        this.plant = plant;
        this.userLabel = userLabel;
        this.lastWatering = LocalDate.now();
        this.lastFertilizing = LocalDate.now();
        this.lastPotReplacement = LocalDate.now();
        this.lastSoilReplacement = LocalDate.now();
        this.message = "";
        this.wateringNotificationSent = false;
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
        LocalDate now = LocalDate.now();

        if(this.needWatering()) {
            this.message += "Water me! ";
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
