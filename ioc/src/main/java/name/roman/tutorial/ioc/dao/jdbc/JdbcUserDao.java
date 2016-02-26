package name.roman.tutorial.ioc.dao.jdbc;

import name.roman.tutorial.ioc.dao.UserDao;
import name.roman.tutorial.ioc.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

/**
 *
 */
public class JdbcUserDao implements UserDao {

    private static final String TABLE_NAME = "users";

    private static final String INSERT_USER_STATEMENT = "INSERT INTO " + TABLE_NAME + " (name) VALUES (:name)";
    private static final String UPDATE_USER_STATEMENT = "UPDATE " + TABLE_NAME + " SET name=:name WHERE id=:id";
    private static final String SELECT_ONE_USER_STATEMENT = "SELECT * FROM " + TABLE_NAME + " WHERE id=:id";
    private static final String SELECT_ALL_USERS_STATEMENT = "SELECT * FROM " + TABLE_NAME;
    private static final String COUNT_ALL_USERS_STATEMENT = "SELECT COUNT(*) FROM " + TABLE_NAME;
    private static final String SELECT_EXISTS_ONE_USER_STATEMENT = COUNT_ALL_USERS_STATEMENT + " WHERE id=:id";

    private static final RowMapper<User> USER_ROW_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("name"));
        }
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        if (!exists(user.getId())) {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            final MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("name", user.getName());
            jdbcTemplate.update(INSERT_USER_STATEMENT, parameters, keyHolder);
            return new User((Integer)keyHolder.getKey(), user.getName());
        } else {
            final MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("name", user.getName())
                    .addValue("id", user.getId());
            jdbcTemplate.update(UPDATE_USER_STATEMENT, parameters);
            return new User(user.getId(), user.getName());
        }
    }

    @Override
    public boolean exists(int id) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_EXISTS_ONE_USER_STATEMENT, parameters, Long.class) == 1;
    }

    @Override
    public User findOne(int id) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_ONE_USER_STATEMENT, parameters, USER_ROW_MAPPER);
    }

    @Override
    public Iterable<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL_USERS_STATEMENT, Collections.emptyMap(), USER_ROW_MAPPER);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT_ALL_USERS_STATEMENT, Collections.emptyMap(), Long.class);
    }
}
