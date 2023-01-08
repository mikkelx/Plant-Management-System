package com.example.pms.home;

import com.example.pms.dto.NotificationEmail;
import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.personalizedPlant.PersonalizedPlantRepository;
import com.example.pms.sign.MailService;
import com.example.pms.user.User;
import com.example.pms.user.UserRepository;
import com.example.pms.user.UserService;
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

    private final UserService userService;
    private final MailService mailService;


    @Transactional
    public List<PersonalizedPlant> getYours2() throws PlantNotFoundException {
        User user = this.getCurrentUser();
        Long userId = user.getUserId();
        List<PersonalizedPlant> list = personalizedPlantRepository.findByUserUserId(userId).orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"));
        this.checkIfActionNeeded(list);
        return list;
    }

    @Transactional
    public PersonalizedPlant getPlantById(Long Id) throws PlantNotFoundException {
        PersonalizedPlant personalizedPlant = personalizedPlantRepository.findByPersonalizedPlantId(Id).orElseThrow(() -> new PlantNotFoundException("Cannot find plant by id: " + Id));
        return personalizedPlant;
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Cannot find user!"));
        return user;
    }

    public String checkIfActionNeeded(List<PersonalizedPlant> personalizedPlantList) {
        for(int i=0; i<personalizedPlantList.size(); i++) {
            String message = "";
            PersonalizedPlant personalizedPlant = personalizedPlantList.get(i);

            if(personalizedPlantList.get(i).needWatering()) {
                message += "Water me! ";
                if(!personalizedPlant.isWateringSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "watering");
                    personalizedPlant.setWateringSent(true);
                }

            }

            if(personalizedPlantList.get(i).needFertilizing()) {
                message += "Fertilize me! ";
                if(!personalizedPlant.isFertilizerSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "fertilizing");
                    personalizedPlant.setFertilizerSent(true);
                }

            }

            if(personalizedPlantList.get(i).needPotReplacement()) {
                message += "Replace my pot! ";
                if(!personalizedPlant.isPotSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "pot replacement");
                    personalizedPlant.setPotSent(true);
                }

            }
            personalizedPlant.setMessage(message);
            personalizedPlantRepository.save(personalizedPlant);
        }

        return "";
    }

    public void sendReminder(String plantLabel, String action) {
        User user = this.getCurrentUser();

        userService.createLog("Reminder was send to user", user);

        mailService.sendMail(new NotificationEmail("Reminder", user.getEmail(),
                ("Your plant need action! Your plant named: " + plantLabel + " need " + action)));
    }



//    @Transactional
//    public List<PersonalizedPlantDto> getYours() throws PlantNotFoundException {
//        User user = this.getCurrentUser();
//        Long userId = user.getUserId();
//        //List<PersonalizedPlant> list = personalizedPlantRepository.findAll();//.orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"));
//        return personalizedPlantRepository.findByUserUserId(userId).orElseThrow(() -> new PlantNotFoundException("Cannot find any plant!"))
//                .stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }

//
//    private PersonalizedPlantDto mapToDto(PersonalizedPlant personalizedPlant) {
//        System.out.println(personalizedPlant.getPlant());
//        return PersonalizedPlantDto.builder()
//                .userLabel(personalizedPlant.getUserLabel())
//                .Id(personalizedPlant.getPersonalizedPlantId())
//                .lastWatering(personalizedPlant.getLastWatering())
//                .lastFertilizing(personalizedPlant.getLastFertilizing())
//                .lastPotReplacement(personalizedPlant.getLastPotReplacement())
//                .plant(personalizedPlant.getPlant())
//                .build();
//    }
}
