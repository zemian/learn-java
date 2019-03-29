package com.zemian.hellojava.spring.tx;

import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Enusre transaction/rollback is working when working with one or more DAOs.
 */
@Service
@Transactional
public class TxService {
    @Autowired
    TxSettingDAO settingDAO;

    @Autowired
    TxUserDAO userDAO;

    public void create(TestParam testParam, Setting setting, User user) {
        settingDAO.create(testParam, setting);
        userDAO.create(testParam, user);
    }
}
