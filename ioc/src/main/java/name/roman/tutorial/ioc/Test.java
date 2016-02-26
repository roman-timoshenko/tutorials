package name.roman.tutorial.ioc;

import name.roman.tutorial.ioc.dao.GroupDao;
import name.roman.tutorial.ioc.dao.UserDao;
import name.roman.tutorial.ioc.dao.UserGroupDao;
import name.roman.tutorial.ioc.dao.jdbc.JdbcGroupDao;
import name.roman.tutorial.ioc.dao.jdbc.JdbcUserDao;
import name.roman.tutorial.ioc.dao.jdbc.JdbcUserGroupDao;
import name.roman.tutorial.ioc.model.Group;
import name.roman.tutorial.ioc.model.User;
import name.roman.tutorial.ioc.service.AccountService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 */
public class Test {


    public static void nonSpringDependencyInjection() throws SQLException {
        /*
            <bean class="org.apache.commons.dbcp2.BasicDataSource" id="dataSource" destroy-method="close">
                <property name="driverClassName" value="org.hsqldb.jdbcDriver" />
                <property name="url" value="jdbc:hsqldb:mem:db"/>
                <property name="username" value="sa" />
                <property name="password" value="" />
            </bean>
         */
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:db");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        /*
            <jdbc:initialize-database>
                <jdbc:script location="db/CREATE_USERS_TABLE.SQL"/>
                <jdbc:script location="db/CREATE_GROUPS_TABLE.SQL"/>
                <jdbc:script location="db/CREATE_USER_GROUPS_TABLE.SQL"/>
            </jdbc:initialize-database>
         */
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(
                new ClassPathResource("db/CREATE_USERS_TABLE.SQL"),
                new ClassPathResource("db/CREATE_GROUPS_TABLE.SQL"),
                new ClassPathResource("db/CREATE_USER_GROUPS_TABLE.SQL")
        ));
        initializer.setEnabled(true);
        initializer.afterPropertiesSet();

        /*
            <bean id="jdbcTemplate" class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
                <constructor-arg type="javax.sql.DataSource" ref="dataSource"/>
            </bean>
         */
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        /*
            <bean id="userDao" class="name.roman.tutorial.ioc.dao.jdbc.JdbcUserDao">
                <constructor-arg ref="jdbcTemplate"/>
            </bean>
         */
        final UserDao userDao = new JdbcUserDao(jdbcTemplate);
        /*
            <bean id="groupDao" class="name.roman.tutorial.ioc.dao.jdbc.JdbcGroupDao">
                <constructor-arg ref="jdbcTemplate"/>
            </bean>
         */
        final GroupDao groupDao = new JdbcGroupDao(jdbcTemplate);
        /*
            <bean id="userGroupDao" class="name.roman.tutorial.ioc.dao.jdbc.JdbcUserGroupDao">
                <constructor-arg ref="jdbcTemplate"/>
            </bean>
         */
        final UserGroupDao userGroupDao = new JdbcUserGroupDao(jdbcTemplate);

        /*
            <bean class="name.roman.tutorial.ioc.service.AccountService" >
                <constructor-arg index="0" ref="userDao"/>
                <constructor-arg index="1" ref="groupDao"/>
                <constructor-arg index="2" ref="userGroupDao"/>
            </bean>
         */
        final AccountService accountService = new AccountService(userDao, groupDao, userGroupDao);

        accountService.save(new User(-1, "Roman"), Arrays.asList(new Group(-1, "Developer"), new Group(-1, "DevOps")));
        System.out.println(accountService.getAllUsersAndGroups());
    }


    public static void springInjection() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        final AccountService accountService = context.getBean(AccountService.class);

        accountService.save(new User(-1, "Roman"), Arrays.asList(new Group(-1, "Developer"), new Group(-1, "DevOps")));
        System.out.println(accountService.getAllUsersAndGroups());
    }

    public static void main(String[] args) throws SQLException {
        final boolean isSpring = false;
        if (isSpring) {
            nonSpringDependencyInjection();
        } else {
            springInjection();
        }
    }

}
