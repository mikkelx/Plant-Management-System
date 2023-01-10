package com.example.pms.plantProperties;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SoilRepository extends JpaRepository<SoilType, Long> {
    SoilType findBySoilName(String soilName);
}
