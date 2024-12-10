package com.example.backendproject;

import com.example.backendproject.config.FirebaseConfig;
import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.domain.model.enums.Gender;
import com.example.backendproject.repository.CustomerRepository;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.LocalDate;

@SpringBootApplication
public class BackEndProjectApplication {
	public static void main(String[] args) {
//		FirebaseConfig.initializeFirebase(); // Khởi tạo Firebase
		SpringApplication.run(BackEndProjectApplication.class, args);

	}

//	@Autowired
//	CustomPasswordEncoder passwordEncoder;
//
//	@Bean
//	CommandLineRunner runner(UserRepository userRepository, CustomerRepository customerRepository) {
//		return args -> {
//			// Create a new user
//			String username = "customer";
//			Gender gender = Gender.MALE;
//			String password = "customer";
//			String firstName = "John";
//			String lastName = "Doe";
//			String email = "johndo2222e@example.com";
//			String phone = "123123123";
//			String address = "123 Main St";
//			LocalDate birthday = LocalDate.of(1990, 1, 1);
//			Customer customer = new Customer(username, gender, passwordEncoder.getPasswordEncoder().encode(password), firstName, lastName, email, phone, address, birthday);
//
//			// Save the user to the database
//			customerRepository.save(customer);
//
//			System.out.println("User inserted into the database: " + customer.getUsername());
//		};
//	}

}
