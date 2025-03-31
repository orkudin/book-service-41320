package kz.iitu.se241m.bookservice.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Lazy // Этот бин будет создан только когда его кто-то запросит (например, applicationContext.getBean(LibraryInfo.class))

public class LibraryInfo {

    @Value("${library.name}")
    private String libraryName;

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public void openLibrary() {
        System.out.println("LibraryInfo Bean: Library '" + libraryName + "' is opening!");
    }

    public void closeLibrary() {
        System.out.println("LibraryInfo Bean: Library '" + libraryName + "' is closing!");
    }

    public String getInfo() {
        return "Welcome to " + libraryName;
    }

    @PostConstruct // Вызывается после создания бина и внедрения зависимостей
    public void initialize() {
        System.out.println("LibraryInfo: @PostConstruct initialize method called.");
        // Можно выполнить какую-то инициализацию здесь
        if (getInfo().isEmpty()) {
            System.out.println("LibraryInfo: No info found, maybe add some defaults?");
        }
    }

    @PreDestroy // Вызывается перед уничтожением бина
    public void cleanup() {
        System.out.println("LibraryInfo: @PreDestroy cleanup method called.");
        // Можно освободить ресурсы здесь
    }
}
