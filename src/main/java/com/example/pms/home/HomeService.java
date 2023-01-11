package com.example.pms.home;

import com.example.pms.dto.NotificationEmail;
import com.example.pms.exceptions.PlantNotFoundException;
import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.personalizedPlant.PersonalizedPlantRepository;
import com.example.pms.sign.MailService;
import com.example.pms.user.User;
import com.example.pms.user.UserRepository;
import com.example.pms.user.UserRole;
import com.example.pms.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserRole getCurrentUserRole() {
        return this.getCurrentUser().getUserRole();
    }

    public String checkIfActionNeeded(List<PersonalizedPlant> personalizedPlantList) {
        for(int i=0; i<personalizedPlantList.size(); i++) {
            String message = "";
            PersonalizedPlant personalizedPlant = personalizedPlantList.get(i);

            if(personalizedPlantList.get(i).needWatering()) {
                message += "Water me! ";
                if(!personalizedPlant.isWateringNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "watering");
                    personalizedPlant.setWateringNotificationSent(true);
                }
            }
            if(personalizedPlantList.get(i).needSunExposure()) {
                message += "Expose me to sun! ";
                if(!personalizedPlant.isSunExposureNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "exposing to sun");
                    personalizedPlant.setSunExposureNotificationSent(true);
                }
            }
            if(personalizedPlantList.get(i).needHarvesting()) {
                message += "Harvest me! ";
                if(!personalizedPlant.isHarvestingSeedingNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "seeds harvesting");
                    personalizedPlant.setHarvestingSeedingNotificationSent(true);
                }
            }
            if(personalizedPlantList.get(i).needPruning()) {
                message += "Prun me! ";
                if(!personalizedPlant.isPruningNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "pruning");
                    personalizedPlant.setPruningNotificationSent(true);
                }
            }
            if(personalizedPlantList.get(i).needPruning()) {
                message += "Clean me! ";
                if(!personalizedPlant.isCleaningLeavesNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "cleaning");
                    personalizedPlant.setCleaningLeavesNotificationSent(true);
                }
            }

            if(personalizedPlantList.get(i).needFertilizing()) {
                message += "Fertilize me! ";
                if(!personalizedPlant.isFertilizerNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "fertilizing");
                    personalizedPlant.setFertilizerNotificationSent(true);
                }

            }

            if(personalizedPlantList.get(i).needPotReplacement()) {
                message += "Replace my pot! ";
                if(!personalizedPlant.isPotNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "pot replacement");
                    personalizedPlant.setPotNotificationSent(true);
                }

            }

            if(personalizedPlantList.get(i).needSoilReplacement()) {
                message += "Replace my soil! ";
                if(!personalizedPlant.isSoilNotificationSent()) {
                    this.sendReminder(personalizedPlant.getUserLabel(), "soil replacement");
                    personalizedPlant.setSoilNotificationSent(true);
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
