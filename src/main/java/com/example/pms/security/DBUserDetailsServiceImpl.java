package com.example.pms.security;


import com.example.pms.user.User;
import com.example.pms.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DBUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optional = repository.findByUsername(username);
//        if (optional.isEmpty()){
//            throw new UsernameNotFoundException("Username not found");
//        }
//        User retrievedUser = optional.get();
//        return new DBUserDetails(retrievedUser);
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("XD"));
        return user;
    }
}