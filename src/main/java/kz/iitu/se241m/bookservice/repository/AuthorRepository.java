package kz.iitu.se241m.bookservice.repository;

import kz.iitu.se241m.bookservice.database.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author save(Author author);
}