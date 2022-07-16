package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao{
    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM AUTHOR where id = ?"))
        {

            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();

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

    @Override
    public Optional<Author> findAuthorByName(String firstName, String lastName) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM AUTHOR where first_name = ? AND last_name = ?"))
        {

            ps.setString(1,firstName);
            ps.setString(2,lastName);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Author foundAuthor = new Author();
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
