package com.example.pms.plant;

import com.example.pms.dto.PlantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping
    public ResponseEntity<List<PlantDto>> getAll() throws Exception{
        ResponseEntity<List<PlantDto>> r = new ResponseEntity<>(plantService.getAll(), HttpStatus.OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(plantService.getAll());
    }

    //TODO - add access only for admin
    @PostMapping("/addNewPlant")
    public void addNewPlantToDB(@RequestBody Plant plant) {
        plantService.addNewPlantToDB(plant);
    }


}
