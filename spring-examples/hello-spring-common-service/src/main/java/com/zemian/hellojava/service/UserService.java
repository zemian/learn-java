package com.zemian.hellojava.service;

import com.zemian.hellojava.cipher.PasswordHasher;
import com.zemian.hellojava.data.dao.Paging;
import com.zemian.hellojava.data.dao.PagingList;
import com.zemian.hellojava.data.domain.User;
import com.zemian.hellojava.data.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A Service to access User.
 *
 * This class is generated by Zemian's CodeGen Toolbox on Jan 2, 2018.
 */
@Service
@Transactional
public class UserService {
    private static Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordHasher passwordHasher;

    @Autowired
    private UserDAO userDAO;

    public void update(User user) {
        userDAO.update(user);
    }

    public User get(String username) {
        return userDAO.get(username);
    }

    public void delete(String username) {
        userDAO.delete(username);
    }

    public boolean exists(String username) {
        return userDAO.exists(username);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public PagingList<User> find(Paging paging) {
        return userDAO.find(paging);
    }

    public void create(User user) {
        LOG.debug("Hashing password for new user {}", user.getUsername());
        String plainPassword = user.getPassword();
        user.setPassword(passwordHasher.createHash(plainPassword));
        userDAO.create(user);
        LOG.debug("{} created", user);
    }

    // == Customize

    public void markForDelete(String username) {
        userDAO.markForDelete(username);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordHasher.verifyPassword(plainPassword, hashedPassword);
    }

    public void changePassword(String username, String plainPassword) {
        String password = passwordHasher.createHash(plainPassword);
        userDAO.changePassword(username, password);
    }
}
