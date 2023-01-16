package com.example.pms.plant;

import com.example.pms.dto.PlantDto;
import com.example.pms.dto.RegisterPersonalizedPlant;
import com.example.pms.dto.RegisterPlant;
import com.example.pms.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;
    private final UserService userService;


    @GetMapping
    public String getAll(Model model) throws Exception{
        List<Plant> plantsList = plantService.getAll();
        model.addAttribute("allPlantsList", plantsList);

        User user = userService.getCurrentUser();
        if(user.getUserRole() == UserRole.ADMIN)
            return "plantsAdmin";

        return "plants";
    }

    @GetMapping(value = "/displayAddingForm")
    public String addNewPersonalizedPlant(Model model) {
        RegisterPlant registerPlant = new RegisterPlant();

        model = this.loadAddingForm(model, registerPlant);

        return "addPlant";
    }

    @PostMapping("/submitNew")
    public String submitPersonalizedPlantForm(@Valid @ModelAttribute("registerPlant") RegisterPlant registerPlant,
                                              BindingResult bindingResult,
                                              Model model) {
        if(bindingResult.hasErrors()) {
            model = this.loadAddingForm(model, registerPlant);

            return "addPlant";
        }

        try {
            plantService.save(registerPlant, bindingResult);
            if(bindingResult.hasErrors()) {
                model = this.loadAddingForm(model, registerPlant);

                return "addPlant";
            }
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/plant";
    }

    @GetMapping("/displayDeleteForm")
    public String deleteGet(Model model) throws Exception{
        RegisterPlant registerPlant = new RegisterPlant();
        model.addAttribute("registerPlant", registerPlant);
        model.addAttribute("allPlants", plantService.getPlants());

        return "deletePlant";
    }

    @GetMapping("/submitDelete")
    public String delete(@Valid @ModelAttribute("registerPlant") RegisterPlant registerPlant,
                         BindingResult bindingResult,
                         Model model) throws Exception {

        try {
            plantService.delete(registerPlant.getPlantName());
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/plant";
    }

    private Model loadAddingForm(Model model, RegisterPlant registerPlant) {
        List<Integer> daysList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 60, 90);

        model.addAttribute("registerPlant", registerPlant);
        model.addAttribute("soilTypeList", plantService.getSoilTypesList());
        model.addAttribute("fertilizerTypesList", plantService.getFertilizerTypesList());
        model.addAttribute("flowerPotTypesList", plantService.getFlowerPotTypesList());
        model.addAttribute("days", daysList);
        return model;
    }




//    @GetMapping
//    public ResponseEntity<List<Plant>> getAll() throws Exception{
//        ResponseEntity<List<Plant>> r = new ResponseEntity<>(plantService.getAll(), HttpStatus.OK);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(plantService.getAll());
//    }
//
//    @GetMapping("/classic")
//    public ResponseEntity<List<Plant>> getAll() throws Exception{
//        ResponseEntity<List<Plant>> r = new ResponseEntity<>(plantService.getAll(), HttpStatus.OK);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(plantService.getAll());
//    }
//
//    @GetMapping("/dto")
//    public ResponseEntity<List<PlantDto>> getAllDto() throws Exception{
//        ResponseEntity<List<PlantDto>> r = new ResponseEntity<>(plantService.getAllDto(), HttpStatus.OK);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(plantService.getAllDto());
//    }


}
