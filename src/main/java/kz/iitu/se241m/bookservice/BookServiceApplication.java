package kz.iitu.se241m.bookservice;

import kz.iitu.se241m.bookservice.database.Book;
import kz.iitu.se241m.bookservice.database.Member;
import kz.iitu.se241m.bookservice.service.AuthorService;
import kz.iitu.se241m.bookservice.service.BookService;
import kz.iitu.se241m.bookservice.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class BookServiceApplication {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
//		SpringApplication.run(BookServiceApplication.class, args);

		applicationContext = SpringApplication.run(BookServiceApplication.class, args);

		System.out.println("Spring Application Context has been initialized.");


		// --- Book Service (Constructor Injection) ---
		System.out.println("\n--- Book Service ---");
		BookService bookService = applicationContext.getBean(BookService.class);
		System.out.println("Initial books: " + bookService.getAllBooks().size());
		bookService.addBook("New Spring Book", "Some Author", "978-1-111-11111-1", 2024);
		System.out.println("Books after adding: " + bookService.getAllBooks().size());


		// --- Author Service (Setter Injection) ---
		System.out.println("\n--- Author Service ---");
		AuthorService authorService = applicationContext.getBean(AuthorService.class);
		System.out.println("Initial authors: " + authorService.getAllAuthors().size());
		try {
			authorService.addAuthor("New", "Author");
			// authorService.addAuthor("", ""); // Это вызовет исключение
		} catch (IllegalArgumentException e) {
			System.err.println("Caught expected exception: " + e.getMessage());
		}
		System.out.println("Authors after adding: " + authorService.getAllAuthors().size());


		// --- Member Service (Field Injection, Prototype Scope) ---
		System.out.println("\n--- Member Service (Prototype) ---");
		MemberService memberService1 = applicationContext.getBean(MemberService.class);
		System.out.println("Registering member...");
		Member member1 = memberService1.registerMember("Charlie Brown", "charlie@example.com");
		System.out.println("Registered member: " + member1);
		System.out.println("All members: " + memberService1.getAllMembers().size());

		// Получаем ЕЩЕ РАЗ бин MemberService - должен быть НОВЫЙ экземпляр из-за @Scope("prototype")
		System.out.println("\n--- Getting Member Service again ---");
		MemberService memberService2 = applicationContext.getBean(MemberService.class);
		System.out.println("MemberService1 instance: " + memberService1);
		System.out.println("MemberService2 instance: " + memberService2);
		System.out.println("Are instances the same? " + (memberService1 == memberService2)); // Должно быть false
		System.out.println("All members via second service instance: " + memberService2.getAllMembers().size()); // Данные общие, т.к. репозиторий - singleton


		System.out.println("\nApplication finished.");
	}

}
