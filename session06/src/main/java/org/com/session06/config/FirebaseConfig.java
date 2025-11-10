package org.com.session06.config;

import com.google.cloud.storage.Storage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    private final String serviceAccountKeyPath = "D:\\PT-HN\\MD4-SpringBoot\\session06\\src\\main\\resources\\firebase.json";

    @Bean
    public Storage storage() throws IOException {
        try (InputStream serviceAccount = new FileInputStream(serviceAccountKeyPath)) {
            return  StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();
        }
    }
}
