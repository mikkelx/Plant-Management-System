package com.example.pms.plant;

import com.example.pms.dto.PlantDto;
import com.example.pms.dto.RegisterPersonalizedPlant;
import com.example.pms.dto.RegisterPlant;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.personalizedPlant.PersonalizedPlantRepository;
import com.example.pms.plantProperties.*;
import com.example.pms.user.User;
import com.example.pms.user.UserService;
import com.example.pms.userLog.PlantLog;
import com.example.pms.userLog.TechnicalLogs;
import com.example.pms.userLog.TechnicalLogsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final UserService userService;
    private final PlantRepository plantRepository;
    private final PersonalizedPlantRepository personalizedPlantRepository;
    private final SoilRepository soilRepository;
    private final FertilizerTypeRepository fertilizerTypeRepository;
    private final FlowerPotRepository flowerPotRepository;
    private final TechnicalLogsRepository technicalLogsRepository;


    @Transactional
    public BindingResult save(RegisterPlant registerPlant, BindingResult bindingResult) throws PlantNotFoundException {
        Boolean con = plantRepository.existsPlantsByPlantName(registerPlant.getPlantName());
        if(con) {
            bindingResult.rejectValue("plantName", "error.user", "Plant: "+ registerPlant.getPlantName() + " is already in system!");
            return bindingResult;
        }

        Plant plant = new Plant();
        plant.setPlantName(registerPlant.getPlantName());
        plant.setWateringTimestampInDays(registerPlant.getWateringTimestampInDays());
        plant.setSunExposureTimeStampInDays(registerPlant.getSunExposureTimeStampInDays());
        plant.setHarvestingSeedingTimestampInDays(registerPlant.getHarvestingSeedingTimestampInDays());
        plant.setPruningTimestampInDays(registerPlant.getPruningTimestampInDays());
        plant.setCleaningLeavesTimestampInDays(registerPlant.getCleaningLeavesTimestampInDays());
        plant.setSoilType(soilRepository.findBySoilName(registerPlant.getSoilTypeName()));
        plant.setFertilizerType(fertilizerTypeRepository.findByFertilizerName(registerPlant.getFertilizerTypeName()));
        plant.setFlowerPot(flowerPotRepository.findByPotSize(registerPlant.getFlowerPotName()));

        User user = userService.getCurrentUser();
        plantRepository.save(plant);

        userService.createLog("Added new system plant: " + registerPlant.getPlantName(), user);
        this.createTechnicalLog("Admin with ID: " + user.getUserId() + " added new system plant with name: " + registerPlant.getPlantName());

        return bindingResult;
    }

    @Transactional
    public void delete(String plantName) throws Exception {
        Plant plant = plantRepository.findByPlantName(plantName).orElseThrow(()->new PlantNotFoundException("Plant not found!"));
//        Long plantId = plantRepository.deleteByPlantName(plantName);
        personalizedPlantRepository.deleteByPlantPlantId(plant.getPlantId());
        plantRepository.delete(plant);
    }

    public void createTechnicalLog(String log) {
        TechnicalLogs technicalLogs = new TechnicalLogs(log);
        technicalLogsRepository.save(technicalLogs);
    }

    public List<PlantDto> getAllDto() {
        return plantRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Plant> getAll() {
        return plantRepository.findAll();
    }

    public List<SoilType> getSoilTypesList() {
        return soilRepository.findAll();
    }

    public List<FertilizerType> getFertilizerTypesList() {
        return fertilizerTypeRepository.findAll();
    }

    public List<FlowerPot> getFlowerPotTypesList() {
        return flowerPotRepository.findAll();
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
