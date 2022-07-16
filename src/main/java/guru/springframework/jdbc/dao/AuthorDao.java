package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    Optional<Author> findAuthorById(Long id);

    Optional<Author> findAuthorByName(String firstName, String lastName);
}
