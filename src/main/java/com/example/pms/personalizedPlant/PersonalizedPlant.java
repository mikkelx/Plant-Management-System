package com.example.pms.personalizedPlant;

import com.example.pms.plant.Plant;
import com.example.pms.user.User;
import lombok.*;
import jakarta.persistence.*;

import org.jetbrains.annotations.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
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
    }

    public PersonalizedPlant(User user, Plant plant, String userLabel) {
        this.user = user;
        this.plant = plant;
        this.userLabel = userLabel;
        this.lastWatering = LocalDate.now();
        this.lastFertilizing = LocalDate.now();
        this.lastPotReplacement = LocalDate.now();
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


    @Override
    public String toString() {
        return "Id: " + personalizedPlantId + " - Label: " + userLabel + this.plant.toString();
    }
}
