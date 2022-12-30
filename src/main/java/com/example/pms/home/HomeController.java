package com.example.pms.home;

import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.personalizedPlant.PersonalizedPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<List<PersonalizedPlantDto>> getYoursPersolizedPlants() throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(homeService.getYours());
    }


}
