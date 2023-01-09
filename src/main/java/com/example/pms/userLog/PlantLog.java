package com.example.pms.userLog;

import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlantLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plantLogId;
    @NotNull
    private String log;
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonalizedPlant personalizedPlant;

    public PlantLog(String log, PersonalizedPlant personalizedPlant) {
        this.log = log;
        this.date = LocalDate.now();
        this.personalizedPlant = personalizedPlant;
    }


}
