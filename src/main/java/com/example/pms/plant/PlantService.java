package com.example.pms.plant;

import com.example.pms.dto.PlantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    public List<PlantDto> getAll() {
        return plantRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Plant> getPlants() {
        return plantRepository.findAll();
    }

    private PlantDto mapToDto(Plant plant) {
        return PlantDto.builder()
                .Id(plant.getPlantId())
                .plantName(plant.getPlantName())
                .wateringTimestampInDays(plant.getWateringTimestampInDays())
                .soil(plant.getSoilType().toString())
                .fertilizer(plant.getFertilizerType().toString())
                .pot(plant.getFlowerPot().toString())
                .build();
    }

    private Plant mapPlantDto(PlantDto plantDto) {
        return Plant.builder()
                .plantId(plantDto.getId())
                .plantName(plantDto.getPlantName())
                .wateringTimestampInDays(plantDto.getWateringTimestampInDays())
                .build();
    }

    public void addNewPlantToDB(Plant plant) {
        plantRepository.save(plant);
    }
}
