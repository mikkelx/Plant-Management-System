package com.example.pms.plantProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;


@Table
@Entity
@NoArgsConstructor
@Getter
public class FertilizerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fertilizerId;
    @NotNull
    private String fertilizerName;
    private int frequencyInDays;


    public FertilizerType(String fertilizerName, int frequencyInDays) {
        this.fertilizerName = fertilizerName;
        this.frequencyInDays = frequencyInDays;
    }

    @Override
    public String toString() {
        return "Nawóz: " + fertilizerName + ", wymiana co " + frequencyInDays + " dni ";
    }
}
