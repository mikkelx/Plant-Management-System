package com.example.pms.personalizedPlant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalizedPlantRepository extends JpaRepository<PersonalizedPlant, Long> {

    Optional<List<PersonalizedPlant>> findByUserUserId(Long userId);
    Optional<PersonalizedPlant> findByPlantPlantId(Long plantId);
}
