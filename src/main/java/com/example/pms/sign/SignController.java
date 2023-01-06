package com.example.pms.sign;

import com.example.pms.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgottenPassword")
    public String forgottenPassword() {
        return "forgotten-password";
    }

    @GetMapping("/signupGet")
    public String signupGet(Model model) {
        RegisterRequest registerRequest = new RegisterRequest();
        model.addAttribute("registerRequest", registerRequest);
        return "signup";
    }

    //TODO - rename mapping
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
//        signService.signup(registerRequest);
//        return new ResponseEntity<>("User registartion successfull - activation link sent to your email!", HttpStatus.OK);
//    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        signService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public String signup(Model model, @ModelAttribute("registerRequest") RegisterRequest registerRequest){
        //model.addAttribute("registerRequest", registerRequest);
        try {
            signService.signup(registerRequest);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/login";
    }



}
