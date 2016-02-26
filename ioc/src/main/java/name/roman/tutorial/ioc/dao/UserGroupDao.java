package name.roman.tutorial.ioc.dao;

/**
 *
 */
public interface UserGroupDao {

    void add(int userId, int groupId);

    void remove(int userId, int groupId);

    Iterable<Integer> getGroups(int userId);

    Iterable<Integer> getUsers(int groupId);

    void removeFromAllGroups(int userId);

    void removeFromAllUsers(int groupId);
}
