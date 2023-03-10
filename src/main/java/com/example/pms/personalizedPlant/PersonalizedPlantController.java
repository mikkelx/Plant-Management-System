package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.dto.RegisterPersonalizedPlant;
import com.example.pms.home.HomeService;
import com.example.pms.plant.Plant;
import com.example.pms.plant.PlantRepository;
import com.example.pms.userLog.PlantLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
                                        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 60, 90);

        model.addAttribute("registerPersonalizedPlant", registerPersonalizedPlant);
        model.addAttribute("allPlants", plants);
        model.addAttribute("days", daysList);
        return "addPersonalizedPlant";
    }

    @PostMapping("/submitNew")
    public String submitPersonalizedPlantForm(@Valid @ModelAttribute("registerPersonalizedPlant") RegisterPersonalizedPlant registerPersonalizedPlant,
                                              BindingResult bindingResult,
                                              Model model) {
        if(bindingResult.hasErrors()) {
            List<Plant> plants = plantRepository.findAll();
            List<Integer> daysList = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 60, 90);

            model.addAttribute("registerPersonalizedPlant", registerPersonalizedPlant);
            model.addAttribute("allPlants", plants);
            model.addAttribute("days", daysList);
            return "addPersonalizedPlant";
        }

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

    @GetMapping("/sun")
    public String sun(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.sunPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }

    @GetMapping("/harvest")
    public String harvest(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.harvestPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }

    @GetMapping("/pruning")
    public String pruning(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.pruningPlantById(Id);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/getById?PersonalizedPlantId=" + Id;
    }

    @GetMapping("/clean")
    public String clean(@RequestParam("PersonalizedPlantId") Long Id, Model model) {
        try {
            personalizedPlantService.cleanPlantById(Id);
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



}
