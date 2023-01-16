package com.example.pms.user;


import com.example.pms.plant.Plant;
import com.example.pms.plant.PlantRepository;
import com.example.pms.personalizedPlant.*;
import com.example.pms.personalizedPlant.PersonalizedPlantRepository;
import com.example.pms.plantProperties.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

//@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner commandLineRunner1(
            UserRepository userRepository,
            PlantRepository plantRepository,
            PersonalizedPlantRepository PersonalizedPlantRepository,
            SoilRepository soilRepository,
            FertilizerTypeRepository fertilizerTypeRepository,
            FlowerPotRepository flowerPotRepository) {
        return args->{

            SoilType peatSoil = new SoilType(
                    "Peat Soil",
                    20
            );

            SoilType humusSoil = new SoilType(
                    "Humus Soil",
                    30
            );

            SoilType sandySoil = new SoilType(
                    "Sandy Soil",
                    20
            );

            SoilType claySoil = new SoilType(
                    "Clay Soil",
                    30
            );

            FertilizerType fertilizerType = new FertilizerType(
                    "Basic fertilizer",
                    14
            );

            FlowerPot flowerPot = new FlowerPot(
                    "Classic Type",
                    20
            );

            Plant plant = new Plant(
                    "Storczyk",
                    1,
                    2,
                    3,
                    4,
                    5,
                    humusSoil,
                    fertilizerType,
                    flowerPot
            );

            Plant plant1 = new Plant(
                    "Dracena Deremeńska",
                    2,
                    3,
                    4,
                    5,
                    6,
                    peatSoil,
                    fertilizerType,
                    flowerPot
            );

            User user1 = new User(
                    "login1",
                    "mail1@gmail.com",
                    passwordEncoder.encode("haslo")
            );
            user1.setEnabled(true);


            User user3 = new User(
                    "admin",
                    "admin@gmail.com",
                    passwordEncoder.encode("haslo")
            );
            user3.setEnabled(true);
            user3.setUserRole(UserRole.ADMIN);

            PersonalizedPlant personalizedPlant1 = new PersonalizedPlant(
                    user1,
                    plant,
                    "Kwiatek w kuchni"
            );

            PersonalizedPlant personalizedPlant2 = new PersonalizedPlant(
                    user1,
                    plant1,
                    "Salon kwiatek"
            );

            user1.addPlant(personalizedPlant1);
            user1.addPlant(personalizedPlant2);

            soilRepository.saveAll(
                    List.of(peatSoil, humusSoil));

            flowerPotRepository.saveAll(
                    List.of(flowerPot)
            );

            fertilizerTypeRepository.saveAll(
                    List.of(fertilizerType)
            );

            plantRepository.saveAll(
                    List.of(plant, plant1));

            userRepository.saveAll(
                    List.of(user1, user3));


            PersonalizedPlantRepository.saveAll(
                    List.of(personalizedPlant1, personalizedPlant2));
        };
    };
}