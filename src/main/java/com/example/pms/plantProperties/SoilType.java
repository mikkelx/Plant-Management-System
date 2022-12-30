package com.example.pms.plantProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Table
@Getter
@NoArgsConstructor
public class SoilType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soilId;
    @NotNull
    private String soilName;
    private int freqOfReplacementInDays;

    public SoilType(String soilName, int freqOfReplacementInDays) {
        this.soilName = soilName;
        this.freqOfReplacementInDays = freqOfReplacementInDays;
    }

    @Override
    public String toString() {
        return "Gleba: " + soilName + ", wymiana co " + freqOfReplacementInDays + " dni " ;
    }
}
