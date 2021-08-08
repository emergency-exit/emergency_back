package com.velog.config.prefix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "cloud.aws.credentials")
@Component
public class S3CredentialsComponent {

    private String accessKey;
    private String secretKey;

}
