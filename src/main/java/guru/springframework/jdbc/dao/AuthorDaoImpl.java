package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {
    public static final String SELECT_AUTHOR_BY_ID = "SELECT * FROM AUTHOR where id = ?";
    public static final String SELECT_AUTHOR_BY_NAME = "SELECT * FROM AUTHOR where first_name = ? AND last_name = ?";
    private static final String INSERT_AUTHOR = "INSERT INTO AUTHOR (first_name,last_name) value(?,?)";

    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_AUTHOR_BY_ID)
        ) {

            Author foundAuthor = new Author();
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            Author authorFromRS = getAuthorFromRS(foundAuthor,resultSet);

            return Optional.of(authorFromRS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> findAuthorByName(String firstName, String lastName) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_AUTHOR_BY_NAME)
        ) {

            Author foundAuthor = new Author();
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet resultSet = ps.executeQuery();

            Author authorFromRS = getAuthorFromRS(foundAuthor, resultSet);

            return Optional.of(authorFromRS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Author saveAuthor(Author author) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_AUTHOR);
                Statement statement = connection.createStatement()
        ) {

            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.execute();

            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                return findAuthorById(resultSet.getLong(1)).orElse(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Author getAuthorFromRS(Author foundAuthor, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            foundAuthor.setId(resultSet.getLong("id"));
            foundAuthor.setFirstName(resultSet.getString("first_name"));
            foundAuthor.setLastName(resultSet.getString("last_name"));
        }
        return foundAuthor;
    }
}
