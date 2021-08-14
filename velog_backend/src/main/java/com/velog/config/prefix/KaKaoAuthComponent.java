package com.velog.config.prefix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kakao.auth")
public class KaKaoAuthComponent {

    private String clientKey;

    private String grant_type;

    private String url;

    private String userInfoUrl;

}
