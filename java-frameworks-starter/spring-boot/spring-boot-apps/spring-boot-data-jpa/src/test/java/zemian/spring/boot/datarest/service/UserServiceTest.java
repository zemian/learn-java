package zemian.spring.boot.datarest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zemian.spring.boot.datarest.model.User;
import zemian.spring.boot.datarest.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by zemian on 10/30/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    public List<User> findAllUsers() {
        Iterable<User> itr = userService.findAll();
        List<User> list = new ArrayList();
        for (User item : itr) {
            list.add(item);
        }
        return list;
    }

    @Test
    public void testfindAllUsers() {
        assertThat(userService, notNullValue());

        List<User> list =  findAllUsers();
        assertThat(list.size(), greaterThanOrEqualTo(3));
    }

    @Test
    public void findOneUser() {
        User user = userService.findOne("test");
        assertThat(user.getUsername(), is("test"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getFirstName(), is("First"));
        assertThat(user.getLastName(), is("Tester"));
        assertThat(user.getEmail(), is("first.tester@test.com"));
        assertThat(user.getCreatedOn(), notNullValue());
    }

    @Test
    public void createUpdateAndDelete() {
        Date tstamp = new Date();
        long count = userService.count();
        assertThat(count, greaterThanOrEqualTo(1L));

        User user =new User();
        user.setUsername("test4");
        user.setPassword("test");
        user.setFirstName("Four");
        user.setLastName("Tester");
        user.setEmail("four.tester@test.com");
        user.setCreatedOn(new Date());

        User userSaved = null;
        try {
            userSaved = userService.save(user);

            assertThat(userService.count(), greaterThan(count));

            assertThat(userSaved.getUsername(), is("test4"));
            assertThat(user.getPassword(), is("test"));
            assertThat(user.getFirstName(), is("Four"));
            assertThat(user.getLastName(), is("Tester"));
            assertThat(user.getEmail(), is("four.tester@test.com"));
            assertThat(user.getCreatedOn(), notNullValue());

            assertThat(userService.exists(userSaved.getUsername()), is(true));
            assertThat(userService.exists("fake_user_1001"), is(false));

            userSaved.setEmail("updated@test.com");
            userService.save(userSaved);

            userSaved = userService.findOne("test4");
            assertThat(userSaved.getUsername(), is("test4"));
            assertThat(userSaved.getEmail(), is("updated@test.com"));

        } finally {
            if (userSaved != null && userSaved.getUsername() != null) {
                userService.delete(userSaved);
            }
        }
    }
}
