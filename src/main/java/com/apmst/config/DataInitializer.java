package com.apmst.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.apmst.entity.User;
import com.apmst.enums.Role;
import com.apmst.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		// Check if admin already existe
		
        if (!userRepository.existsByEmail("admin@apmst.com")) {

            User admin = new User();
            admin.setFullName("System Admin");
            admin.setEmail("admin@apmst.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            admin.setPoints(0);

            userRepository.save(admin);

            System.out.println("✅ Admin user created successfully!");
            System.out.println("📧 Email: admin@apmst.com");
            System.out.println("🔑 Password: admin123");
        } else {
            System.out.println("✅ Admin user already exists!");
        }
    }

		
	}


