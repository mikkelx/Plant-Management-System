package com.example.pms.plant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findByPlantId(Long Id);
    Optional<Plant> findByPlantName(String plantName);

    Boolean existsPlantsByPlantName(String plantName);
    Long deleteByPlantName(String plantName);


}
