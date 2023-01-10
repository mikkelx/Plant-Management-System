package com.example.pms.sign;

import com.example.pms.dto.RegisterRequest;
import com.example.pms.exceptions.ActivationException;
import com.example.pms.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //reseting password
    @GetMapping("/forgottenPassword")
    public String forgottenPassword(Model model) {
        RegisterRequest registerRequest = new RegisterRequest();

        model.addAttribute("registerRequest", registerRequest);
        return "forgotten-password";
    }

    @PostMapping("/sendMailToReset")
    public String passwordResetSendMail(Model model,
                                    @ModelAttribute("registerRequest") RegisterRequest emailRequest){
        try {
            signService.sendResetEmail(emailRequest.getEmail());
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);

            return "error";
        }
        model.addAttribute("exceptionMessage", "Reset password link was sent to your email!");
        return "error";
    }

    @GetMapping("/passwordReset/{token}")
    public String passwordResetGet(@PathVariable String token, Model model) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setToken(token);

        try {
            signService.verifyAccount(token);
        } catch (ActivationException exception) {
            model.addAttribute("exceptionMessage", "Password reset error");
        }

        model.addAttribute("registerRequest", registerRequest);
        return "forgotten-password_submit";
    }


    @PostMapping("/passwordReset/{token}")
    public String passwordResetPost(Model model,
                         @ModelAttribute("registerRequest") RegisterRequest registerRequest, @PathVariable String token) {
        try {
            signService.changePassword(registerRequest.getPassword(), registerRequest.getPassword_repeat(), registerRequest.getToken());
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);

            return "error";
        }

        return "redirect:/login";
    }

    //register new user

    @GetMapping("/signupGet")
    public String signupGet(Model model) {
        RegisterRequest registerRequest = new RegisterRequest();
        model.addAttribute("registerRequest", registerRequest);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model,
                         @Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "signup";
        }

        signService.signup(registerRequest, bindingResult);
        if(bindingResult.hasErrors()) {
            return "signup";
        }

//        try {
//            signService.signup(registerRequest, bindingResult);
//            if(bindingResult.hasErrors()) {
//                return "signup";
//            }
//        } catch (Exception exception) {
//            String exceptionMessage = exception.getMessage();
//            model.addAttribute("exceptionMessage", exceptionMessage);
//            return "error";
//        }

        model.addAttribute("exceptionMessage", "Activation link was sent to your email!");
        return "error";
    }

    @GetMapping("/accountVerification/{token}")
    public String verifyAccount(@PathVariable String token, Model model) {
        try {
            signService.verifyAccount(token);
            userService.createLog("User account confirmed");
        } catch (ActivationException exception) {
            model.addAttribute("exceptionMessage", "Account activation error");
        }
        model.addAttribute("exceptionMessage", "Account activated successfully");
        return "error";
    }




    //TODO - rename mapping
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
//        signService.signup(registerRequest);
//        return new ResponseEntity<>("User registartion successfull - activation link sent to your email!", HttpStatus.OK);
//    }

//    @GetMapping("/accountVerification/{token}")
//    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
//        signService.verifyAccount(token);
//        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
//    }





}
