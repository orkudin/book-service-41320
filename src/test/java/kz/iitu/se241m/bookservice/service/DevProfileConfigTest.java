package kz.iitu.se241m.bookservice.service;


import kz.iitu.se241m.bookservice.config.DataSourceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest // Загружает полный контекст приложения
@ActiveProfiles("dev") // Запускаем тест с активным профилем "dev"
class DevProfileConfigTest {

    @Autowired // Внедряем контекст
    private ApplicationContext context;

    @Test
    void dataSourceInfoBeanIsFromDevConfig() {
        DataSourceInfo dsInfo = context.getBean(DataSourceInfo.class);
        assertNotNull(dsInfo);
        // Проверяем, что URL соответствует dev-конфигурации
        assertTrue(dsInfo.toString().contains("jdbc:h2:mem:devdb"));
        System.out.println("DevProfileConfigTest: Verified DEV DataSourceInfo: " + dsInfo);
    }
}
