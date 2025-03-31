package kz.iitu.se241m.bookservice.repository;

import kz.iitu.se241m.bookservice.database.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // Указываем Spring, что это компонент слоя доступа к данным
public class BookRepository {

    // Имитация базы данных книг в памяти
    private List<Book> books = new ArrayList<>();
    private long nextId = 1;

    public BookRepository() {
        // Предзаполним несколькими книгами для примера
        save(new Book(null, "Война и мир", "Лев Толстой", "978-5-389-04772-7", 1869));
        save(new Book(null, "1984", "Джордж Оруэлл", "978-5-17-086595-1", 1949));
        save(new Book(null, "Мастер и Маргарита", "Михаил Булгаков", "978-5-389-10628-5", 1967));
    }

    // Метод для получения всех книг
    public List<Book> findAll() {
        System.out.println("BookRepository: Finding all books...");
        return new ArrayList<>(books); // Возвращаем копию, чтобы внешний код не изменил наш список
    }

    // Метод для добавления книги (имитация)
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        } else {
            // Удаляем старую версию, если она есть (простая имитация update)
            books.removeIf(b -> b.getId().equals(book.getId()));
        }
        books.add(book);
        System.out.println("BookRepository: Saved book - " + book.getTitle());
        return book;
    }

    // Метод поиска по ID (может понадобиться позже)
    public Book findById(Long id) {
        System.out.println("BookRepository: Finding book by id: " + id);
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}