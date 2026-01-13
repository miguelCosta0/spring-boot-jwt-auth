package jwt_auth.User;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final RowMapper<User> userRowMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");

            return new User(id, name, email, password);
        }
    };

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> getUserById(long id) {
        var sql = "SELECT * FROM User_ WHERE id = ?";
        Object[] args = {id};
        int[] argTypes = {Types.INTEGER};

        List<User> result = jdbcTemplate.query(sql, args, argTypes, userRowMapper);

        return Optional.ofNullable(result.isEmpty() ? null : result.getFirst());
    }

    public Optional<User> getUserByEmail(String email) {
        var sql = "SELECT * FROM User_ WHERE email = ?";
        Object[] args = {email};
        int[] argTypes = {Types.VARCHAR};

        List<User> result = jdbcTemplate.query(sql, args, argTypes, userRowMapper);

        return Optional.ofNullable(result.isEmpty() ? null : result.getFirst());
    }

    public int createUser(User user) {
        var sql = "INSERT INTO User_ (name, email, password) VALUES (?, ?, ?)";
        Object[] args = {user.getName(), user.getEmail(), user.getPassword()};
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

        return jdbcTemplate.update(sql, args, argTypes);
    }

    /*
     * public int updateUser(long id, User user)
     * 
     * public int deleteUser(long id)
     */

}
