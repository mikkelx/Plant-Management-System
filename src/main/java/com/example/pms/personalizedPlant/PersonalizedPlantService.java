package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.dto.RegisterPersonalizedPlant;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.plant.PlantRepository;
import com.example.pms.user.User;
import com.example.pms.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalizedPlantService {
    private final UserService userService;
    private final PersonalizedPlantRepository personalizedPlantRepository;
    private final PlantRepository plantRepository;

    @Transactional
    public void save(RegisterPersonalizedPlant registerPersonalizedPlant) throws PlantNotFoundException {
        PersonalizedPlant save = new PersonalizedPlant();
        User user = userService.getCurrentUser();
        save.setUser(user);
        save.setUserLabel(registerPersonalizedPlant.getUserLabel());
        save.setLastWatering(LocalDate.now().minusDays(registerPersonalizedPlant.getLastWatering()));
        save.setLastFertilizing(LocalDate.now().minusDays(registerPersonalizedPlant.getLastFertilizing()));
        save.setLastPotReplacement(LocalDate.now().minusDays(registerPersonalizedPlant.getLastPotReplacement()));
        save.setPlant(plantRepository.findByPlantName(registerPersonalizedPlant.getPlantName())
                .orElseThrow(() -> new PlantNotFoundException("Plant not found in db!")));

        userService.createLog("Added new plant: " + registerPersonalizedPlant.getPlantName(), user);
        personalizedPlantRepository.save(save);
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
