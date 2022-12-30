package com.example.pms.personalizedPlant;

import com.example.pms.dto.PersonalizedPlantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personalizedplant")
@RequiredArgsConstructor
public class PersonalizedPlantController {
    private final PersonalizedPlantService personalizedPlantService;

    @PostMapping
    public ResponseEntity<PersonalizedPlantDto> createPresonalizedPlant(@RequestBody PersonalizedPlantDto personalizedPlantDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personalizedPlantService.save(personalizedPlantDto));
    }

    @GetMapping
    public ResponseEntity<List<PersonalizedPlantDto>> getYoursPersolizedPlants() throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personalizedPlantService.getYours());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonalizedPlantDto>> getAllPersolizedPlants() throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personalizedPlantService.getYours());
    }


}
