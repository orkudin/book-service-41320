package kz.iitu.se241m.bookservice.service;


import kz.iitu.se241m.bookservice.database.Book;
import kz.iitu.se241m.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Указываем Spring, что это компонент слоя бизнес-логики
public class BookService {

    private final BookRepository bookRepository; // Зависимость

    // Внедрение зависимости через конструктор (Constructor Injection)
    // Spring автоматически найдет бин типа BookRepository и передаст его сюда
    @Autowired
    // @Autowired здесь можно не писать для конструктора с одним параметром в новых версиях Spring, но для ясности оставим
    public BookService(BookRepository bookRepository) {
        System.out.println("BookService: Constructor called, injecting BookRepository.");
        this.bookRepository = bookRepository;
    }

    // Метод сервиса, который использует репозиторий
    public List<Book> getAllBooks() {
        System.out.println("BookService: Requesting all books from repository.");
        // Тут может быть какая-то бизнес-логика перед/после вызова репозитория
        return bookRepository.findAll();
    }

    // Пример другого метода сервиса
    public Book findBookById(Long id) {
        System.out.println("BookService: Requesting book by id: " + id);
        Book book = bookRepository.findById(id);
        if (book == null) {
            System.out.println("BookService: Book with id " + id + " not found.");
            // Можно бросить исключение или вернуть Optional
        }
        return book;
    }

    // Метод для добавления книги через сервис
    public Book addBook(String title, String authorName, String isbn, int year) {
        System.out.println("BookService: Attempting to add a new book.");
        // Здесь может быть логика валидации данных перед сохранением
        Book newBook = new Book(null, title, authorName, isbn, year);
        return bookRepository.save(newBook);
    }
}