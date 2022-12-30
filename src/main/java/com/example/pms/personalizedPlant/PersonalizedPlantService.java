package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.plant.PlantRepository;
import com.example.pms.user.User;
import com.example.pms.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalizedPlantService {
    private final UserService userService;
    private final PersonalizedPlantRepository personalizedPlantRepository;
    private final PlantRepository plantRepository;

    @Transactional
    public PersonalizedPlantDto save(PersonalizedPlantDto personalizedPlantDto) {
       PersonalizedPlant save = personalizedPlantRepository.save(mapPersonalizedPlantDto(personalizedPlantDto));
       personalizedPlantDto.setId(save.getPersonalizedPlantId());
       return personalizedPlantDto;
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
