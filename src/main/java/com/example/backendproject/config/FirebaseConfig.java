package com.example.backendproject.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
//    @Bean
//    FirebaseApp firebaseApp() throws IOException {
//        ClassPathResource resource = new ClassPathResource("src/main/resources/serviceAccountKey.json");
//        InputStream serviceAccount = resource.getInputStream();
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setProjectId("quanlythuvien-a3de6\n")
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setStorageBucket("api-app.appspot.com")
//                .setDatabaseUrl("https://fir-fileuploadapi-default-rtdb.firebaseio.com")
//                .build();
//
//        return FirebaseApp.initializeApp(options);
//    }
}
