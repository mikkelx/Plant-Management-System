package com.example.pms.plantProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Table
@NoArgsConstructor
@Getter
public class FlowerPot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long potId;
    @NotNull
    private String potSize;
    private int freqOfReplacementInDays;


    public FlowerPot(String potSize, int freqOfReplacementInDays) {
        this.potSize = potSize;
        this.freqOfReplacementInDays = freqOfReplacementInDays;
    }

    @Override
    public String toString() {
        return "Doniczka, wielkość: " + potSize + ", wymiana co " + freqOfReplacementInDays + " dni ";
    }
}
