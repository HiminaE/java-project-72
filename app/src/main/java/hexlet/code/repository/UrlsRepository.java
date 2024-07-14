package hexlet.code.repository;

import hexlet.code.model.Url;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Getter
@Setter
public class UrlsRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        var time = new Timestamp(System.currentTimeMillis());
        try (var conn = dataSource.getConnection();
             var prepareStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStmt.setString(1, url.getName());
            prepareStmt.setTimestamp(2, time);
            prepareStmt.executeUpdate();

            var generatedKey = prepareStmt.getGeneratedKeys();
            if (generatedKey.next()) {
                url.setId(generatedKey.getLong(1));
                url.setCreatedAt(time);
            }
        }
    }

    public static List<Url> getUrls() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var site = new Url(id, name, createdAt);
                result.add(site);
            }
            return result;
        }
    }

    public static Optional<Url> getById(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                var site = new Url(id, name, createdAt);
                return Optional.of(site);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @SneakyThrows
    public static Optional<Url> getByName(String name) {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var id = resultSet.getLong("id");
                var createdAt = resultSet.getTimestamp("created_at");
                var site = new Url(id, name, createdAt);
                return Optional.of(site);
            } else {
                return Optional.empty();
            }
        }
    }
    public static Optional<Url> findByName(String urlName) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, urlName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                long id = resultSet.getLong("id");
                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }
}
