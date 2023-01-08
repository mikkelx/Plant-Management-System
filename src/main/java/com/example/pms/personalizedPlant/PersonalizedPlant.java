package com.example.pms.personalizedPlant;

import com.example.pms.plant.Plant;
import com.example.pms.user.User;
import lombok.*;
import jakarta.persistence.*;

import org.jetbrains.annotations.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
    private String message;
    private boolean wateringSent;
    private boolean fertilizerSent;
    private boolean potSent;

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
                             LocalDate lastFertilizing, LocalDate lastPotReplacement) {
        this.userLabel = userLabel;
        this.lastWatering = lastWatering;
        this.lastFertilizing = lastFertilizing;
        this.user = user;
        this.plant = plant;
        this.lastPotReplacement = lastPotReplacement;
        this.message = "";
        this.wateringSent = false;
        this.fertilizerSent = false;
        this.potSent = false;

    }

    public PersonalizedPlant(User user, Plant plant, String userLabel) {
        this.user = user;
        this.plant = plant;
        this.userLabel = userLabel;
        this.lastWatering = LocalDate.now();
        this.lastFertilizing = LocalDate.now();
        this.lastPotReplacement = LocalDate.now();
        this.message = "";
        this.wateringSent = false;
        this.fertilizerSent = false;
        this.potSent = false;
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

    public boolean isWateringSent() {
        return wateringSent;
    }

    public boolean isFertilizerSent() {
        return fertilizerSent;
    }

    public boolean isPotSent() {
        return potSent;
    }

    @Override
    public String toString() {
        return "Id: " + personalizedPlantId + " - Label: " + userLabel + this.plant.toString();
    }
}
