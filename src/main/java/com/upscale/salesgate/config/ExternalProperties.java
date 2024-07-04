package com.upscale.salesgate.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties("external")
public class ExternalProperties {
    String seaShellUrl;
}
