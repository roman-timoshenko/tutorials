package name.roman.tutorial.ioc.dao.jdbc;

import name.roman.tutorial.ioc.dao.UserGroupDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 *
 */
public class JdbcUserGroupDao implements UserGroupDao {

    private static final String TABLE_NAME = "user_groups";

    private static final String ADD_USER_TO_GROUP_STATEMENT =
            "MERGE INTO " + TABLE_NAME + " USING (VALUES(:user_id, :group_id)) AS vals(user_id, group_id) " +
            "ON " + TABLE_NAME + ".USER_ID = vals.user_id AND " + TABLE_NAME + ".GROUP_ID = vals.group_id " +
            "WHEN NOT MATCHED THEN INSERT VALUES vals.user_id, vals.group_id";
    private static final String REMOVE_USER_FROM_GROUP_STATEMENT =
            "DELETE FROM " + TABLE_NAME + " WHERE USER_ID = :user_id and GROUP_ID = :group_id";
    private static final String FIND_USERS_BY_GROUP_STATEMENT =
            "SELECT user_id FROM " + TABLE_NAME + " WHERE group_id = :group_id";
    private static final String FIND_GROUPS_BY_USER_STATEMENT =
            "SELECT group_id FROM " + TABLE_NAME + " WHERE user_id = :user_id";

    private static final String REMOVE_USER_FROM_ALL_GROUPS_STATEMENT =
            "DELETE FROM " + TABLE_NAME + " WHERE USER_ID = :user_id";
    private static final String REMOVE_ALL_USERS_FROM_GROUP_STATEMENT =
            "DELETE FROM " + TABLE_NAME + " WHERE GROUP_ID = :group_id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserGroupDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(int userId, int groupId) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("group_id", groupId);
        jdbcTemplate.update(ADD_USER_TO_GROUP_STATEMENT, parameters);
    }

    @Override
    public void remove(int userId, int groupId) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("group_id", groupId);
        jdbcTemplate.update(REMOVE_USER_FROM_GROUP_STATEMENT, parameters);
    }

    @Override
    public Iterable<Integer> getGroups(int userId) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", userId);
        return jdbcTemplate.query(FIND_GROUPS_BY_USER_STATEMENT, parameters, (rs, rowNum) -> rs.getInt("group_id"));
    }

    @Override
    public Iterable<Integer> getUsers(int groupId) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("group_id", groupId);
        return jdbcTemplate.query(FIND_USERS_BY_GROUP_STATEMENT, parameters, (rs, rowNum) -> rs.getInt("user_id"));
    }

    @Override
    public void removeFromAllGroups(int userId) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", userId);
        jdbcTemplate.update(REMOVE_USER_FROM_ALL_GROUPS_STATEMENT, parameters);
    }

    @Override
    public void removeFromAllUsers(int groupId) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("group_id", groupId);
        jdbcTemplate.update(REMOVE_ALL_USERS_FROM_GROUP_STATEMENT, parameters);
    }
}
