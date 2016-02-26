package name.roman.tutorial.ioc.dao.jdbc;

import name.roman.tutorial.ioc.dao.GroupDao;
import name.roman.tutorial.ioc.model.Group;
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
public class JdbcGroupDao implements GroupDao {

    private static final String TABLE_NAME = "groups";

    private static final String INSERT_GROUP_STATEMENT = "INSERT INTO " + TABLE_NAME + " (name) VALUES (:name)";
    private static final String UPDATE_GROUP_STATEMENT = "UPDATE " + TABLE_NAME + " SET name=:name WHERE id=:id";
    private static final String SELECT_ONE_GROUP_STATEMENT = "SELECT * FROM " + TABLE_NAME + " WHERE id=:id";
    private static final String SELECT_ALL_GROUPS_STATEMENT = "SELECT * FROM " + TABLE_NAME;
    private static final String COUNT_ALL_GROUPS_STATEMENT = "SELECT COUNT(*) FROM " + TABLE_NAME;
    private static final String SELECT_EXISTS_ONE_GROUP_STATEMENT = COUNT_ALL_GROUPS_STATEMENT + " WHERE id=:id";

    private static final RowMapper<Group> GROUP_ROW_MAPPER = new RowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Group(rs.getInt("id"), rs.getString("name"));
        }
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcGroupDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group save(Group group) {
        if (!exists(group.getId())) {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            final MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("name", group.getName());
            jdbcTemplate.update(INSERT_GROUP_STATEMENT, parameters, keyHolder);
            return new Group((Integer)keyHolder.getKey(), group.getName());
        } else {
            final MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("name", group.getName())
                    .addValue("id", group.getId());
            jdbcTemplate.update(UPDATE_GROUP_STATEMENT, parameters);
            return new Group(group.getId(), group.getName());
        }
    }

    @Override
    public boolean exists(int id) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_EXISTS_ONE_GROUP_STATEMENT, parameters, Long.class) == 1;
    }

    @Override
    public Group findOne(int id) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_ONE_GROUP_STATEMENT, parameters, GROUP_ROW_MAPPER);
    }

    @Override
    public Iterable<Group> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GROUPS_STATEMENT, Collections.emptyMap(), GROUP_ROW_MAPPER);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT_ALL_GROUPS_STATEMENT, Collections.emptyMap(), Long.class);
    }
}
