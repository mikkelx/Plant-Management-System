package com.example.pms.user;

import com.example.pms.userLog.UserLog;
import com.example.pms.userLog.UserLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;

    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    public void addNewUserToDB(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    public void createLog(String log) {
        UserLog userLog = new UserLog(log, this.getCurrentUser());
        userLogRepository.save(userLog);
    }

    public void createLog(String log, User user) {
        UserLog userLog = new UserLog(log, user);
        userLogRepository.save(userLog);
    }

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Cannot find user!"));
        //user.setPassword("*protected*");
        return user;
    }

}
