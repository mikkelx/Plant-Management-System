package com.example.pms.user;

import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.userLog.UserLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public String getAllUsers(Model model) throws Exception{
        model.addAttribute("usersList", userService.getUsersList());

        return "displayUsersAdmin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long userId,
                         Model model) throws Exception{

    if(userId == userService.getCurrentUserId()) {
        model.addAttribute("errorMessage", "You cannot delete this user!");
        model.addAttribute("usersList", userService.getUsersList());
        return "displayUsersAdmin";
    }
        try {
            userService.deleteById(userId);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }

        return "redirect:/user";
    }

    @GetMapping("/logs")
    public String getLogs(@RequestParam("userId") Long userId,
                         Model model) throws Exception{

        if(userId == userService.getCurrentUserId()) {
            model.addAttribute("errorMessage", "You cannot see admin logs!");
            model.addAttribute("usersList", userService.getUsersList());
            return "displayUsersAdmin";
        }
        try {
            List<UserLog> userLogList = userService.getUserLogs(userId);
            model.addAttribute("userLogList", userLogList);
            return "userLogs";

        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            model.addAttribute("exceptionMessage", exceptionMessage);
            return "error";
        }
    }


//    @GetMapping
//    public List<User> getUsersList() {
//        return userService.getUsersList();
//    }
//
//    @PostMapping
//    public void addNewUserToDB(@RequestBody User user) {
//        userService.addNewUserToDB(user);
//    }

    //TODO
//    @PostMapping("/passchange")
//    public ResponseEntity<String> changePassword(@RequestBody String email) {
//
//        return new ResponseEntity<>("Link to change your password was sent!", HttpStatus.OK);
//    }
//
//    @GetMapping("/passchange/{token}")
//    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
//        authService.verifyAccount(token);
//        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
//    }

}
