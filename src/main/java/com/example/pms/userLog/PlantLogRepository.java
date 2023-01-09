package com.example.pms.userLog;

import com.example.pms.personalizedPlant.PersonalizedPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantLogRepository extends JpaRepository<PlantLog, Long> {
    List<PlantLog> findLogByPersonalizedPlantPersonalizedPlantId(Long Id);
}
