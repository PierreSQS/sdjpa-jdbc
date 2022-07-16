package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao{
    public static final String SELECT_AUTHOR_BY_ID = "SELECT * FROM AUTHOR where id = ?";
    public static final String SELECT_AUTHOR_BY_NAME = "SELECT * FROM AUTHOR where first_name = ? AND last_name = ?";

    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return findAuthor(id, null,null, SELECT_AUTHOR_BY_ID);
    }

    @Override
    public Optional<Author> findAuthorByName(String firstName, String lastName) {
        return findAuthor(null,firstName,lastName,SELECT_AUTHOR_BY_NAME);
    }

    private Optional<Author> findAuthor(Long id, String firstName, String lastName, String sqlCmd) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlCmd))
        {

            Author foundAuthor = new Author();

            if (id != null) {
                ps.setLong(1,id);
            } else {
                ps.setString(1, firstName);
                ps.setString(2, lastName);
            }

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {

                foundAuthor.setId(resultSet.getLong("id"));
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
