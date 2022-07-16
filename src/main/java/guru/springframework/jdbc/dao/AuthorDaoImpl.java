package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao{
    @Override
    public Optional<Author> getById(Long id) {
        return Optional.empty();
    }
}
