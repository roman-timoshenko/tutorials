package name.roman.tutorial.ioc.dao;

import name.roman.tutorial.ioc.model.User;

/**
 *
 */
public interface UserDao {

    User save(User user);

    User findOne(int id);

    Iterable<User> findAll();

    boolean exists(int id);

    long count();
}
