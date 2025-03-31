package kz.iitu.se241m.bookservice.config;

import kz.iitu.se241m.bookservice.service.AuthorService;
import kz.iitu.se241m.bookservice.service.BookService;
import kz.iitu.se241m.bookservice.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class AppConfig {
    // Определяем бин LibraryInfo с помощью @Bean
    // Имя бина по умолчанию будет именем метода - "libraryInfo"
    @Bean(initMethod = "openLibrary", destroyMethod = "closeLibrary")
    public LibraryInfo libraryInfoBean() { // Имя метода может быть любым, но лучше осмысленным
        System.out.println("AppConfig: Creating LibraryInfo bean...");
        LibraryInfo info = new LibraryInfo();
        // Здесь можно выполнить дополнительную настройку бина перед возвратом
        // Например, установить имя из properties, но пока сделаем проще
        info.setLibraryName("Configured IITU Library");
        return info;
    }

    @Bean("serviceInitializer")
    public Object serviceInitializer() {
        System.out.println("AppConfig: initializing service Initializer...");
        return new Object();
    }

    @Bean("mainServiceRunner")
    @DependsOn("serviceInitializer") // Этот бин будет создан ПОСЛЕ serviceInitializer
    public Object mainServiceRunner() {
        System.out.println("AppConfig: initializing main Service runner (depends on Initializer)...");
        // Запуск основного сервиса, который требует предварительной инициализации
        return new Object();
    }

    // Простой сервис
    class GreetingService {
        private String greetingTemplate;
        public GreetingService(String template) { this.greetingTemplate = template; }
        public String greet(String name) { return String.format(greetingTemplate, name); }
    }

    // Фабричный метод @Bean
    @Bean
    public GreetingService greetingService(@Value("${library.name:Welcome, user!}") String template) {
        // Логика: используем значение из custom.properties или дефолтное
        System.out.println("AppConfig: Creating GreetingService with template: " + template);
        return new GreetingService(template);
    }

    @Bean
    public CommandLineRunner demoDataRunner(BookService bookService,
                                            AuthorService authorService,
                                            MemberService memberServicePrototype1, // Запросим один экземпляр прототипа
                                            MemberService memberServicePrototype2, // Запросим второй экземпляр прототипа
                                            LibraryInfo libraryInfo,
                                            DataSourceInfo dataSourceInfo, // Может быть null, если профиль не активен
                                            GreetingService greetingService) {
        return args -> { // Лямбда-выражение для метода run
            System.out.println("\n CommandLineRunner: Application started! Running demo logic...");
            System.out.println("==========================================================");

            // Используем LibraryInfo bean
            System.out.println("--- Checking LibraryInfo bean from AppConfig ---");
            System.out.println(libraryInfo.getInfo());
            System.out.println(greetingService.greet("Student"));


            // Используем DataSourceInfo (если есть)
            System.out.println("--- Checking Profile specific DataSourceInfo bean ---");
            if (dataSourceInfo != null) {
                System.out.println("Active DataSource: " + dataSourceInfo);
                dataSourceInfo.connect();
            } else {
                System.out.println("No active DataSource profile detected.");
            }


            System.out.println("\n--- Using BookService ---");
            System.out.println("Books count: " + bookService.getAllBooks().size());
            System.out.println("Initial books: " + bookService.getAllBooks().size());
            bookService.addBook("New Spring Book", "Some Author", "978-1-111-11111-1", 2024);
            System.out.println("Books after adding: " + bookService.getAllBooks().size());


            System.out.println("\n--- Using AuthorService ---");
            System.out.println("Authors count: " + authorService.getAllAuthors().size());
            authorService.findAuthorById(2L).ifPresent(a -> System.out.println("Found author 2: " + a.getFirstName()));
            try {
                authorService.addAuthor("New", "Author");
                // authorService.addAuthor("", ""); // Это вызовет исключение
            } catch (IllegalArgumentException e) {
                System.err.println("Caught expected exception: " + e.getMessage());
            }
            System.out.println("Authors after adding: " + authorService.getAllAuthors().size());


            System.out.println("\n--- Using MemberService (Prototype check) ---");
            System.out.println("MemberService Instance 1: " + memberServicePrototype1);
            System.out.println("MemberService Instance 2: " + memberServicePrototype2);
            System.out.println("Are MemberService instances same? " + (memberServicePrototype1 == memberServicePrototype2)); // false

            memberServicePrototype1.registerMember("Runner Member", "runner@example.com");
            System.out.println("Members count: " + memberServicePrototype1.getAllMembers().size());

            System.out.println("==========================================================");
            System.out.println("CommandLineRunner: Demo logic finished.\n");
        };
    }
}
