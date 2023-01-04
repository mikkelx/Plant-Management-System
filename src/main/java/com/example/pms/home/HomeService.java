package com.example.pms.home;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.personalizedPlant.PersonalizedPlantRepository;
import com.example.pms.user.User;
import com.example.pms.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserRepository userRepository;
    private final PersonalizedPlantRepository personalizedPlantRepository;


    @Transactional
    public List<PersonalizedPlantDto> getYours() throws PlantNotFoundException {
        User user = this.getCurrentUser();
        Long userId = user.getUserId();
        //List<PersonalizedPlant> list = personalizedPlantRepository.findAll();//.orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"));
        return personalizedPlantRepository.findByUserUserId(userId).orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"))
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PersonalizedPlant> getYours2() throws PlantNotFoundException {
        User user = this.getCurrentUser();
        Long userId = user.getUserId();
        //List<PersonalizedPlant> list = personalizedPlantRepository.findAll();//.orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"));
        return personalizedPlantRepository.findByUserUserId(userId).orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"));
    }

    @Transactional
    public PersonalizedPlant getPlantById(Long Id) throws PlantNotFoundException {
        return personalizedPlantRepository.findByPlantPlantId(Id).orElseThrow(() -> new PlantNotFoundException("Cannot find plant by id: " + Id));
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Cannot find user!"));
        return user;
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
}
