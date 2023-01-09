package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.dto.RegisterPersonalizedPlant;
import com.example.pms.home.HomeService;
import com.example.pms.plant.Plant;
import com.example.pms.plant.PlantRepository;
import com.example.pms.userLog.PlantLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/personalizedplant")
@RequiredArgsConstructor
public class PersonalizedPlantController {
    private final PersonalizedPlantService personalizedPlantService;
    private final PlantRepository plantRepository;
    private final HomeService homeService;



    @GetMapping("/getById")
    public String getPlantById(@RequestParam("PersonalizedPlantId") Long Id, Model model) throws Exception{
        PersonalizedPlant personalizedPlant = homeService.getPlantById(Id);
        model.addAttribute("personalizedPlant", personalizedPlant);
        return "personalizedPlant";
    }

    @GetMapping(value = "/displayAddingForm")
    public String addNewPersonalizedPlant(Model model) {
        RegisterPersonalizedPlant registerPersonalizedPlant = new RegisterPersonalizedPlant();

        List<Plant> plants = plantRepository.findAll();
        List<Integer> daysList = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                                        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30);

        model.addAttribute("registerPersonalizedPlant", registerPersonalizedPlant);
        model.addAttribute("allPlants", plants);
        model.addAttribute("days", daysList);
        return "addPersonalizedPlant";
    }

    @PostMapping("/submitNew")
    public String submitPersonalizedPlantForm(Model model,
                                              @ModelAttribute("registerPersonalizedPlant") RegisterPersonalizedPlant registerPersonalizedPlant) {

        try {
            personalizedPlantService.save(registerPersonalizedPlant);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("PersonalizedPlantId") Long Id, Model model) throws Exception{

        try {
            personalizedPlantService.delete(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/";
    }

    @GetMapping("/getPlantLogs")
    public String getPlantLogs(@RequestParam("PersonalizedPlantId") Long Id, Model model) throws Exception{
        List<PlantLog> plantLogsList = personalizedPlantService.getPlantLogs(Id);
        model.addAttribute("plantLogsList", plantLogsList);
        return "plantLogs";
    }

    @GetMapping("/water")
    public String water(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.waterPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }

    @GetMapping("/fertilizer")
    public String fertilizer(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.fertilizerPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }

    @GetMapping("/pot")
    public String pot(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.potPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }

    @GetMapping("/soil")
    public String soil(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.soilPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }


    //    @GetMapping
//    public ResponseEntity<List<PersonalizedPlantDto>> getYoursPersolizedPlants() throws Exception{
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(personalizedPlantService.getYours());
//    }
//

//    @PostMapping
//    public ResponseEntity<PersonalizedPlantDto> createPresonalizedPlant(@RequestBody PersonalizedPlantDto personalizedPlantDto) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(personalizedPlantService.saveDto(personalizedPlantDto));
//    }


    //submitting new plant
//    @PostMapping("/submitPersonalizedPlantForm")
//    public String submitPersonalizedPlantForm(PersonalizedPlantDto personalizedPlantDto) {
//        System.out.println(personalizedPlantDto.getUserLabel());
//
//        return "userhome";
//    }

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
