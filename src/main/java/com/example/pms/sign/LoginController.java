package com.example.pms.sign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "login_page";
    }

    @GetMapping("/demo")
    public String demo(Model model) {
        model.addAttribute("theDate", LocalDate.now());

        return "test";
    }

    @GetMapping("/demo2")
    public String demo2(Model model) {
        model.addAttribute("theDate", LocalDate.now());

        return "product";
    }
}
