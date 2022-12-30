package com.example.pms.userLog;



import com.example.pms.user.User;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity(name = "user_logs")
@Table
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLogId;
    @NotNull
    private String logData;
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserLog(String logData) {
        this.logData = logData;
        this.date = LocalDate.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
