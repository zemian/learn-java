package com.zemian.hellojava.spring.tx;

import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * We purposely do not add @Transactional here to compare to one used in TxService.
 */
@Service
public class NoTxService {
    @Autowired
    TxSettingDAO settingDAO;

    @Autowired
    TxUserDAO userDAO;

    public void create(TestParam testParam, Setting setting, User user) {
        settingDAO.create(testParam, setting);
        userDAO.create(testParam, user);
    }
}
