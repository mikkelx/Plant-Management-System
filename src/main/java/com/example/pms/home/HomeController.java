package com.example.pms.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.pms.dto.PersonalizedPlantDto;
import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.personalizedPlant.PersonalizedPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

//    @GetMapping
//    public ResponseEntity<List<PersonalizedPlantDto>> getYoursPersolizedPlants() throws Exception{
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(homeService.getYours());
//    }

    @GetMapping
    public String getYoursPersolizedPlants(Model model) throws Exception{
        model.addAttribute("yourPlantsList", homeService.getYours2());
        return "userhome";
    }

    @GetMapping("/getById")
    public String getPlantById(@RequestParam("PersonalizedPlantId") Long Id, Model model) throws Exception{
        PersonalizedPlant personalizedPlant = homeService.getPlantById(Id);
        model.addAttribute("personalizedPlant", personalizedPlant);
        return "personalizedPlant";
    }





//    @GetMapping("getById")
//    public String getPlantById1(@RequestParam("PersonalizedPlantId") Long Id, Model model) throws Exception{
//        PersonalizedPlant personalizedPlant = homeService.getPlantById(Id);
//        model.addAttribute("personalizedPlant", personalizedPlant);
//        return "personalizedPlant";
//    }

//    @GetMapping("/demo2")
//    public String demo2(org.springframework.ui.Model model) {
//        model.addAttribute("theDate", LocalDate.now());
//
//        return "product";
//    }


}
