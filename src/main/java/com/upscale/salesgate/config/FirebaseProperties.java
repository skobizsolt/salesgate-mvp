package com.upscale.salesgate.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties("firebase")
public class FirebaseProperties {
    String serviceAccountPath;
}
