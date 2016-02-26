package name.roman.tutorial.ioc.service;

import name.roman.tutorial.ioc.dao.GroupDao;
import name.roman.tutorial.ioc.dao.UserDao;
import name.roman.tutorial.ioc.dao.UserGroupDao;
import name.roman.tutorial.ioc.model.Group;
import name.roman.tutorial.ioc.model.User;

import java.util.*;

/**
 *
 */
public class AccountService {
    private final UserDao userDao;
    private final GroupDao groupDao;
    private final UserGroupDao userGroupDao;

    public AccountService(UserDao userDao, GroupDao groupDao, UserGroupDao userGroupDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.userGroupDao = userGroupDao;
    }

    public void save(User user, Iterable<Group> groups) {
        final User savedUser = userDao.save(user);
        userGroupDao.removeFromAllGroups(user.getId());
        for (final Group group : groups) {
            final Group savedGroup = groupDao.save(group);
            userGroupDao.add(savedUser.getId(), savedGroup.getId());
        }
    }

    public Map<User, Collection<Group>> getAllUsersAndGroups() {
        final Map<User, Collection<Group>> result = new HashMap<>();
        for (final User user : userDao.findAll()) {
            final List<Group> groups = new ArrayList<>();
            for (final Integer groupId : userGroupDao.getGroups(user.getId())) {
                groups.add(groupDao.findOne(groupId));
            }
            result.put(user, groups);
        }
        return result;
    }
}
