package com.example.backendproject;

import com.example.backendproject.domain.model.User;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackEndProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackEndProjectApplication.class, args);

	}

//	@Autowired
//	CustomPasswordEncoder passwordEncoder;
//
//	@Bean
//	CommandLineRunner runner(UserRepository userRepository) {
//		return args -> {
//			// Create a new user
//			User user = new User();
//			user.setUsername("test1");
//			user.setPassword(passwordEncoder.getPasswordEncoder().encode("test1"));  // In real applications, make sure to hash the password before saving!
//
//			// Save the user to the database
//			userRepository.save(user);
//
//			System.out.println("User inserted into the database: " + user.getUsername());
//		};
//	}

}
