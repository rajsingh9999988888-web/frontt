package com.babyadoption.config;

import com.babyadoption.model.User;
import com.babyadoption.model.User.UserRole;
import com.babyadoption.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.findByEmail("admin@134").orElseGet(() -> {
            User admin = new User();
            admin.setEmail("admin@134");
            admin.setPassword("9382019794");
            admin.setRole(UserRole.ADMIN);
            admin.setMobile("9382019794");
            return userRepository.save(admin);
        });
    }
}


