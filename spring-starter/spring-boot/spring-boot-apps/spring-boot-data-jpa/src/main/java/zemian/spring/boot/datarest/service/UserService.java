package zemian.spring.boot.datarest.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zemian.spring.boot.datarest.model.User;

/**
 * Created by zemian on 10/30/16.
 */
@Repository
public interface UserService extends CrudRepository<User, String> {
}
