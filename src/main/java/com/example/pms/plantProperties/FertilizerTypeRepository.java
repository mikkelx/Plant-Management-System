package com.example.pms.plantProperties;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FertilizerTypeRepository extends JpaRepository<FertilizerType, Long> {
    FertilizerType findByFertilizerName(String fertilizerName);
}
