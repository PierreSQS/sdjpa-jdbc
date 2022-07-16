package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void findAuthorById() {
        Optional<Author> foundAuthOpt = authorDao.findAuthorById(2L);
        assertThat(foundAuthOpt).isPresent();

        foundAuthOpt.ifPresent(author ->
                System.out.printf("#### the found author name: %s ####%n",author.getLastName()));
    }

    @Test
    void findAuthorByName() {
        Optional<Author> foundAuthOpt = authorDao.findAuthorByName("Craig", "Walls");
        assertThat(foundAuthOpt).isPresent();

        foundAuthOpt.ifPresent(author ->
                System.out.printf("#### the found author name: %s ####%n",author.getLastName()));
    }
}