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

//    @OneToMany(mappedBy = "plant",
//            orphanRemoval = true,
//            cascade = CascadeType.ALL)
//    private Set<PersonalizedPlant> personalizedPlants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "soilId")
    private SoilType soilType;

    @ManyToOne
    @JoinColumn(name = "fertilizerId")
    private FertilizerType fertilizerType;

    @ManyToOne
    @JoinColumn(name = "potId")
    private FlowerPot flowerPot;

    public Plant(String plantName, int wateringTimestampInDays, SoilType soilType, FertilizerType fertilizerType, FlowerPot flowerPot) {
        this.plantName = plantName;
        this.wateringTimestampInDays = wateringTimestampInDays;
        this.soilType = soilType;
        this.fertilizerType = fertilizerType;
        this.flowerPot = flowerPot;
    }

    @Override
    public String toString() {
        return "Gatunek: " + this.plantName;// + " " + soilType.toString();
    }

    public String getPlantName() {
        return plantName;
    }
}
