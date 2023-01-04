package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.home.HomeService;
import com.example.pms.plant.Plant;
import com.example.pms.plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/personalizedplant")
@RequiredArgsConstructor
public class PersonalizedPlantController {
    private final PersonalizedPlantService personalizedPlantService;
    private final PlantRepository plantRepository;
    private final HomeService homeService;


//    @GetMapping
//    public ResponseEntity<List<PersonalizedPlantDto>> getYoursPersolizedPlants() throws Exception{
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(personalizedPlantService.getYours());
//    }
//

    @PostMapping
    public ResponseEntity<PersonalizedPlantDto> createPresonalizedPlant(@RequestBody PersonalizedPlantDto personalizedPlantDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personalizedPlantService.save(personalizedPlantDto));
    }

    @GetMapping("/getById")
    public String getPlantById(@RequestParam("PersonalizedPlantId") Long Id, Model model) throws Exception{
        PersonalizedPlant personalizedPlant = homeService.getPlantById(Id);
        model.addAttribute("personalizedPlant", personalizedPlant);
        return "personalizedPlant";
    }

    @GetMapping(value = "/addNew")
    public String addNewPersonalizedPlant(Model model) {
        List<Plant> plants = plantRepository.findAll();
        model.addAttribute("AllPlants", plants);
        return "addPersonalizedPlant";
    }

//    @GetMapping
//    public ResponseEntity<List<PersonalizedPlantDto>> getYoursPersolizedPlants() throws Exception{
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(personalizedPlantService.getYours());
//    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonalizedPlantDto>> getAllPersolizedPlants() throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personalizedPlantService.getYours());
    }


}
