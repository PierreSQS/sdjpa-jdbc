package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao{
    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Author> getById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM AUTHOR where id =" + id);

            if (resultSet.next()) {
                Author foundAuthor = new Author();
                foundAuthor.setId(id);
                foundAuthor.setFirstName(resultSet.getString("first_name"));
                foundAuthor.setLastName(resultSet.getString("last_name"));

                return Optional.of(foundAuthor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
