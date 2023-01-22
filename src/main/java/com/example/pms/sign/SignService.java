package com.example.pms.sign;

import com.example.pms.dto.NotificationEmail;
import com.example.pms.dto.RegisterRequest;
import com.example.pms.exceptions.ActivationException;
import com.example.pms.user.User;
import com.example.pms.user.UserRepository;
import com.example.pms.user.UserRole;
import com.example.pms.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final UserService userService;

    @Transactional
    public BindingResult signup(RegisterRequest registerRequest, BindingResult bindingResult) throws IllegalStateException{
        boolean userExists = userRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if(userExists) {
            bindingResult.rejectValue("email", "error.user", "Email: "+ registerRequest.getEmail() + " is already registered!");
            return bindingResult;
//            throw new IllegalStateException("Email: "+ registerRequest.getEmail() + " is already registered!");
        }
        userExists = userRepository.findByUsername(registerRequest.getUsername()).isPresent();
        if(userExists) {
            bindingResult.rejectValue("username", "error.user", "Username: "+ registerRequest.getUsername() + " is already taken!");
            return bindingResult;
            //throw new IllegalStateException("Username: "+ registerRequest.getUsername() + " is already taken!");
        }
        if(!registerRequest.getPassword().equals(registerRequest.getPassword_repeat())) {
            bindingResult.rejectValue("password_repeat", "error.user", "Passwords are not matching!");
            return bindingResult;
            //throw new IllegalStateException("Passwords are not matching!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);
        user.setUserRole(UserRole.USER);

        userRepository.save(user);
        userService.createLog("User registered, activation email sent", user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(),
                "Thanks for signing up for PMS, click on the link to activate your account:" +
                        "http://localhost:8100/auth/accountVerification/" + token));

        return bindingResult;
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new ActivationException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }


    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ActivationException("Invalid username " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void sendResetEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ActivationException("Email is wrong!"));
        VerificationToken verificationToken = verificationTokenRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new ActivationException("Email is wrong!"));

        userService.createLog("Password rest link sent to email", user);

        mailService.sendMail(new NotificationEmail("Password reset", user.getEmail(),
                "THere is your link to reset password, click on the link to change:" +
                        "http://localhost:8100/auth/passwordReset/" + verificationToken.getToken()));
    }

    public void changePassword(String newPassword, String newPasswordRepeat, String token) throws ActivationException{
        if(newPassword.equals(newPasswordRepeat)) {
            Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
            verificationToken.orElseThrow(() -> new ActivationException("Invalid Token"));
            User user = verificationToken.get().getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.createLog("Password changed", user);
            userRepository.save(user);
            return;
        }
        throw new ActivationException("Passwords are not matching!");
    }

    public boolean isAlreadyLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser"))
            return true;

        return false;
    }

}
