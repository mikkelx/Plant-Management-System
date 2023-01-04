package com.example.pms.plant;

import com.example.pms.dto.PlantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

//    @GetMapping
//    public ResponseEntity<List<Plant>> getAll() throws Exception{
//        ResponseEntity<List<Plant>> r = new ResponseEntity<>(plantService.getAll(), HttpStatus.OK);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(plantService.getAll());
//    }

    @GetMapping("/classic")
    public ResponseEntity<List<Plant>> getAll() throws Exception{
        ResponseEntity<List<Plant>> r = new ResponseEntity<>(plantService.getAll(), HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(plantService.getAll());
    }

    @GetMapping("/dto")
    public ResponseEntity<List<PlantDto>> getAllDto() throws Exception{
        ResponseEntity<List<PlantDto>> r = new ResponseEntity<>(plantService.getAllDto(), HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(plantService.getAllDto());
    }

    @GetMapping
    public String getAll(Model model) throws Exception{
        List<Plant> plantsList = plantService.getAll();
        model.addAttribute("allPlantsList", plantsList);
        return "plants";
    }


    //TODO - add access only for admin
    @PostMapping("/addNewPlant")
    public void addNewPlantToDB(@RequestBody Plant plant) {
        plantService.addNewPlantToDB(plant);
    }


}
