package name.roman.tutorial.ioc.dao;

import name.roman.tutorial.ioc.model.Group;

/**
 *
 */
public interface GroupDao {

    Group save(Group user);

    Group findOne(int id);

    Iterable<Group> findAll();

    boolean exists(int id);

    long count();
}
