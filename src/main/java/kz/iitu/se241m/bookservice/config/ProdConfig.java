package kz.iitu.se241m.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod") // Эта конфигурация активна только при активном профиле "prod"
public class ProdConfig {

    @Bean
    public DataSourceInfo dataSourceInfo() {
        System.out.println("ProdConfig: Creating PROD DataSourceInfo bean...");
        return new DataSourceInfo("jdbc:postgresql://prod_host:5432/librarydb", "prod_user");
    }
}