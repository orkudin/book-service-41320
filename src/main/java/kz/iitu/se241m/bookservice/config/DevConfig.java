package kz.iitu.se241m.bookservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") // Эта конфигурация активна только при активном профиле "dev"
public class DevConfig {
    //Lab 2 Field Injection в Configuration классе:
    @Value("${spring.profiles.active}")
    private String configUser;

    @Bean
    public DataSourceInfo dataSourceInfo() {
        System.out.println("DevConfig: Creating DEV DataSourceInfo bean... " + configUser);
        return new DataSourceInfo("jdbc:h2:mem:devdb", "dev_user");
    }
}