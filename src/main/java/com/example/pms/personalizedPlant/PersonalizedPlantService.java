package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.dto.RegisterPersonalizedPlant;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.plant.PlantRepository;
import com.example.pms.user.User;
import com.example.pms.user.UserService;
import com.example.pms.userLog.PlantLog;
import com.example.pms.userLog.PlantLogRepository;
import com.example.pms.userLog.UserLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalizedPlantService {
    private final UserService userService;
    private final PersonalizedPlantRepository personalizedPlantRepository;
    private final PlantRepository plantRepository;
    private final PlantLogRepository plantLogRepository;

    @Transactional
    public void save(RegisterPersonalizedPlant registerPersonalizedPlant) throws PlantNotFoundException {
        PersonalizedPlant save = new PersonalizedPlant();
        User user = userService.getCurrentUser();
        save.setUser(user);
        save.setUserLabel(registerPersonalizedPlant.getUserLabel());
        save.setLastWatering(LocalDate.now().minusDays(registerPersonalizedPlant.getLastWatering()));
        save.setLastFertilizing(LocalDate.now().minusDays(registerPersonalizedPlant.getLastFertilizing()));
        save.setLastPotReplacement(LocalDate.now().minusDays(registerPersonalizedPlant.getLastPotReplacement()));
        save.setLastSoilReplacement(LocalDate.now().minusDays(registerPersonalizedPlant.getLastSoilReplacement()));
        save.setPlant(plantRepository.findByPlantName(registerPersonalizedPlant.getPlantName())
                .orElseThrow(() -> new PlantNotFoundException("Plant not found in db!")));

        userService.createLog("Added new plant: " + registerPersonalizedPlant.getPlantName(), user);
        this.createPlantLog(registerPersonalizedPlant.getUserLabel() + " created!", save);

        personalizedPlantRepository.save(save);
    }

    @Transactional
    public void delete(Long Id) throws Exception {
        personalizedPlantRepository.deleteById(Id);
    }

    public List<PlantLog> getPlantLogs(Long Id) {
        List<PlantLog> plantLogList = plantLogRepository.findLogByPersonalizedPlantPersonalizedPlantId(Id);

        return plantLogList;
    }


    @Transactional
    public List<PersonalizedPlantDto> getYours() throws PlantNotFoundException {
        User user = userService.getCurrentUser();
        Long userId = user.getUserId();
        //List<PersonalizedPlant> list = personalizedPlantRepository.findAll();//.orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"));
        return personalizedPlantRepository.findByUserUserId(userId).orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"))
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void waterPlantById(Long Id) throws PlantNotFoundException {
        PersonalizedPlant personalizedPlant = personalizedPlantRepository.findByPersonalizedPlantId(Id).orElseThrow(() -> new PlantNotFoundException("Plant cannot be found"));

        personalizedPlant.setLastWatering(LocalDate.now());
        personalizedPlant.setMessage(personalizedPlant.getMessage().replaceAll("Water me!", ""));
        personalizedPlant.setWateringNotificationSent(false);

        createPlantLog("Watering", personalizedPlant);

        personalizedPlantRepository.save(personalizedPlant);
    }

    public void fertilizerPlantById(Long Id) throws PlantNotFoundException {
        PersonalizedPlant personalizedPlant = personalizedPlantRepository.findByPersonalizedPlantId(Id).orElseThrow(() -> new PlantNotFoundException("Plant cannot be found"));

        personalizedPlant.setLastFertilizing(LocalDate.now());
        personalizedPlant.setMessage(personalizedPlant.getMessage().replaceAll("Fertilize me! ", ""));
        personalizedPlant.setFertilizerNotificationSent(false);

        createPlantLog("Feritilizing", personalizedPlant);

        personalizedPlantRepository.save(personalizedPlant);
    }

    public void potPlantById(Long Id) throws PlantNotFoundException {
        PersonalizedPlant personalizedPlant = personalizedPlantRepository.findByPersonalizedPlantId(Id).orElseThrow(() -> new PlantNotFoundException("Plant cannot be found"));

        personalizedPlant.setLastPotReplacement(LocalDate.now());
        personalizedPlant.setMessage(personalizedPlant.getMessage().replaceAll("Replace my pot! ", ""));
        personalizedPlant.setPotNotificationSent(false);

        createPlantLog("Pot change", personalizedPlant);

        personalizedPlantRepository.save(personalizedPlant);
    }

    public void soilPlantById(Long Id) throws PlantNotFoundException {
        PersonalizedPlant personalizedPlant = personalizedPlantRepository.findByPersonalizedPlantId(Id).orElseThrow(() -> new PlantNotFoundException("Plant cannot be found"));

        personalizedPlant.setLastSoilReplacement(LocalDate.now());
        personalizedPlant.setMessage(personalizedPlant.getMessage().replaceAll("Replace my soil! ", ""));
        personalizedPlant.setSoilNotificationSent(false);

        createPlantLog("Soil change", personalizedPlant);

        personalizedPlantRepository.save(personalizedPlant);
    }

    public void createPlantLog(String log, PersonalizedPlant personalizedPlant) {
        PlantLog plantLog = new PlantLog(log, personalizedPlant);
        plantLogRepository.save(plantLog);
    }

    @Transactional
    public PersonalizedPlantDto saveDto(PersonalizedPlantDto personalizedPlantDto) {
        PersonalizedPlant save = personalizedPlantRepository.save(mapPersonalizedPlantDto(personalizedPlantDto));
        personalizedPlantDto.setId(save.getPersonalizedPlantId());
        return personalizedPlantDto;
    }

//    //TODO - to delete - user have access only to his plants
    @Transactional
    public List<PersonalizedPlantDto> getAll() {
        return personalizedPlantRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PersonalizedPlantDto mapToDto(PersonalizedPlant personalizedPlant) {
        System.out.println(personalizedPlant.getPlant());
        return PersonalizedPlantDto.builder()
                .userLabel(personalizedPlant.getUserLabel())
                .Id(personalizedPlant.getPersonalizedPlantId())
                .lastWatering(personalizedPlant.getLastWatering())
                .lastFertilizing(personalizedPlant.getLastFertilizing())
                .lastPotReplacement(personalizedPlant.getLastPotReplacement())
                .plant(personalizedPlant.getPlant())
                .build();
    }

    private PersonalizedPlant mapPersonalizedPlantDto(PersonalizedPlantDto personalizedPlantDto) {
        return PersonalizedPlant.builder()
                .personalizedPlantId(personalizedPlantDto.getId())
                .userLabel(personalizedPlantDto.getUserLabel())
                .lastWatering(personalizedPlantDto.getLastWatering())
                .lastFertilizing(personalizedPlantDto.getLastFertilizing())
                .lastPotReplacement(personalizedPlantDto.getLastPotReplacement())
                .plant(personalizedPlantDto.getPlant())
                //.plant(plantRepository.findByPlantId(personalizedPlantDto.getPlantId()))
                //.user(authService.getCurrentUser())
                //.plant(personalizedPlantDto.getPlant())
                .build();
    }
}
