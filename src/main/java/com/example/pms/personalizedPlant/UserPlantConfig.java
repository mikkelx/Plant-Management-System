package com.example.pms.personalizedPlant;//package com.example.pms.PersonalizedPlant;
//
//import com.example.pms.user.User;
//import com.example.pms.user.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.util.List;
//
//@Configuration
//public class PersonalizedPlantConfig {
//
//
//
//    @Bean
//    CommandLineRunner commandLineRunner1(
//            UserRepository repository) {
//        return args->{
//            PersonalizedPlant user1 = new PersonalizedPlant(
//                    1,
//                    1,
//                    "XD",
//                    LocalDate.now()
//            );
//
//            PersonalizedPlant user2 = new PersonalizedPlant(
//                    1,
//                    1,
//                    "XD",
//                    LocalDate.now()
//            );
//
//            repository.saveAll(
//                    List.of(user1, user2));
//        };
//    };
//}
