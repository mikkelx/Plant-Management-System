package com.example.pms.personalizedPlant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface PersonalizedPlantRepository extends JpaRepository<PersonalizedPlant, Long> {

    Optional<List<PersonalizedPlant>> findByUserUserId(Long userId);
    Optional<PersonalizedPlant> findByPlantPlantId(Long plantId);
    Optional<PersonalizedPlant> findByPersonalizedPlantId(Long Id);
    @Modifying
    Long deleteByPlantPlantId(Long plantId);
}
