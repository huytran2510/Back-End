package com.example.backendproject;

import com.example.backendproject.domain.model.User;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BackEndProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackEndProjectApplication.class, args);

	}

//	@Autowired
//	CustomPasswordEncoder passwordEncoder;
//
//	@Bean
//	CommandLineRunner runner(UserRepository userRepository, CustomerRepository customerRepository) {
//		return args -> {
//			// Create a new user
//			String username = "testUser";
//			Gender gender = Gender.MALE;
//			String password = "password123";
//			String firstName = "John";
//			String lastName = "Doe";
//			String email = "johndoe@example.com";
//			String phone = "123456789";
//			String address = "123 Main St";
//			LocalDate birthday = LocalDate.of(1990, 1, 1);
//			Customer customer = new Customer(username, gender, passwordEncoder.getPasswordEncoder().encode("test1"), firstName, lastName, email, phone, address, birthday);
//
//			// Save the user to the database
//			customerRepository.save(customer);
//
//			System.out.println("User inserted into the database: " + customer.getUsername());
//		};
//	}

}
