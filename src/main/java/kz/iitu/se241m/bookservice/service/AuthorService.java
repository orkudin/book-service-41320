package kz.iitu.se241m.bookservice.service;

import kz.iitu.se241m.bookservice.database.Author;
import kz.iitu.se241m.bookservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    // Setter Injection - Внедрение зависимости через сеттер
    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        System.out.println("AuthorService: Setter injection for AuthorRepository.");
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        System.out.println("AuthorService: Requesting all authors.");
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        System.out.println("AuthorService: Requesting author by id: " + id);
        return authorRepository.findById(id);
    }

    public Author addAuthor(String firstName, String lastName) {
        System.out.println("AuthorService: Adding new author.");
        // Проверка, что имя не пустое (простая бизнес-логика)
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Author first and last names cannot be empty.");
        }
        Author author = new Author(null, firstName, lastName);
        return authorRepository.save(author);
    }
}