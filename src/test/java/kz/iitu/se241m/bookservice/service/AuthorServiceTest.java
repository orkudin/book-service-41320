package kz.iitu.se241m.bookservice.service;


import kz.iitu.se241m.bookservice.database.Author;
import kz.iitu.se241m.bookservice.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Инициализация Mockito
class AuthorServiceTest {

    @Mock // Создаем мок (заглушку) для репозитория
    private AuthorRepository authorRepository;

    @InjectMocks // Создаем экземпляр AuthorService и внедряем в него моки (@Mock)
    private AuthorService authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
        // Метод, выполняемый перед каждым тестом
    void setUp() {
        // Инициализация тестовых данных
        author1 = new Author(1L, "Test", "Author1");
        author2 = new Author(2L, "Test", "Author2");
        // Нет необходимости вызывать setAuthorRepository вручную, MockitoExtension сделает это через @InjectMocks
        System.out.println("Setup complete for test.");
    }

    @Test
    void getAllAuthors() {
        System.out.println("Testing getAllAuthors...");
        // 1. Arrange (Настройка): Говорим моку, что вернуть при вызове findAll()
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        // 2. Act (Действие): Вызываем тестируемый метод
        List<Author> authors = authorService.getAllAuthors();

        // 3. Assert (Проверка): Проверяем результат
        assertNotNull(authors);
        assertEquals(2, authors.size());
        assertEquals("Author1", authors.get(0).getLastName());
        verify(authorRepository, times(1)).findAll(); // Убедимся, что метод репозитория был вызван ровно 1 раз
        System.out.println("getAllAuthors test passed.");
    }

    @Test
    void findAuthorById_Found() {
        System.out.println("Testing findAuthorById_Found...");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        Optional<Author> result = authorService.findAuthorById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getFirstName());
        verify(authorRepository, times(1)).findById(1L);
        System.out.println("findAuthorById_Found test passed.");
    }

    @Test
    void findAuthorById_NotFound() {
        System.out.println("Testing findAuthorById_NotFound...");
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Author> result = authorService.findAuthorById(99L);

        assertFalse(result.isPresent());
        verify(authorRepository, times(1)).findById(99L);
        System.out.println("findAuthorById_NotFound test passed.");
    }

    @Test
    void addAuthor_Success() {
        System.out.println("Testing addAuthor_Success...");
        Author newAuthor = new Author(null, "New", "Dude");
        Author savedAuthor = new Author(3L, "New", "Dude"); // Автор после "сохранения" с ID

        // Настраиваем мок для save: когда передаем автора без ID, возвращаем автора с ID=3
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        // Используем any(Author.class) т.к. мы не можем точно знать какой объект будет создан внутри сервиса

        Author result = authorService.addAuthor("New", "Dude");

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New", result.getFirstName());
        verify(authorRepository, times(1)).save(any(Author.class));
        System.out.println("addAuthor_Success test passed.");
    }

    @Test
    void addAuthor_EmptyFirstName_ThrowsException() {
        System.out.println("Testing addAuthor_EmptyFirstName_ThrowsException...");
        // Проверяем, что вызов метода с пустым именем бросает исключение
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.addAuthor("", "LastName");
        });

        assertEquals("Author first and last names cannot be empty.", exception.getMessage());
        // Убедимся, что метод save не вызывался
        verify(authorRepository, never()).save(any(Author.class));
        System.out.println("addAuthor_EmptyFirstName_ThrowsException test passed.");
    }
}