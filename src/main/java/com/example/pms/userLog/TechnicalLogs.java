package com.example.pms.userLog;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
public class TechnicalLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalLogId;
    @NotNull
    private String logData;
    @NotNull
    private LocalDate date;

    public TechnicalLogs(String logData) {
        this.logData = logData;
        this.date = LocalDate.now();
    }
}
