package com.example.pms.user;

import com.example.pms.personalizedPlant.PersonalizedPlant;
import com.example.pms.userLog.UserLog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    Set<PersonalizedPlant> personalizedPlants = new HashSet<>();

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    Set<UserLog> userLogs = new HashSet<>();


    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = UserRole.USER;
    }

    public boolean validatePassword(String password) {
        if(password.equals(this.password))
            return true;
        return false;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void addPlant(PersonalizedPlant personalizedPlant) {
        this.personalizedPlants.add(personalizedPlant);
        personalizedPlant.setUser(this);
        this.createLog(new UserLog("Added new plant"));
    }

    public void createLog(UserLog userLog) {
        this.userLogs.add(userLog);
        userLog.setUser(this);
    }


}