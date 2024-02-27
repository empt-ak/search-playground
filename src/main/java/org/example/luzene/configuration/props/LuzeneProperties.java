package org.example.luzene.configuration.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record LuzeneProperties(int mismatchDistance) {

}
