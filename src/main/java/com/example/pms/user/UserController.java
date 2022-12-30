package com.example.pms.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsersList() {
        return userService.getUsersList();
    }

    @PostMapping
    public void addNewUserToDB(@RequestBody User user) {
        userService.addNewUserToDB(user);
    }

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
