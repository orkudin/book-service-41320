package kz.iitu.se241m.bookservice.repository;

import kz.iitu.se241m.bookservice.database.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAuthorRepository implements AuthorRepository {

    private final Map<Long, Author> authors = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public InMemoryAuthorRepository() {
        // Начальные данные
        save(new Author(null, "Лев", "Толстой"));
        save(new Author(null, "Джордж", "Оруэлл"));
        save(new Author(null, "Михаил", "Булгаков"));
        save(new Author(null, "Мухтар", "Ауэзов"));
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authors.get(id));
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            author.setId(sequence.incrementAndGet());
        }
        authors.put(author.getId(), author);
        System.out.println("InMemoryAuthorRepository: Saved author - " + author.getFirstName() + " " + author.getLastName());
        return author;
    }
}