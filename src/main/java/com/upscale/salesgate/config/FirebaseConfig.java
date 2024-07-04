package com.upscale.salesgate.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @SneakyThrows
    @PostConstruct
    public void initFireBase() {
        final FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/firebase/salesgateServiceAccount.json");

        final FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
