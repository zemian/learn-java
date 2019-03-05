package com.zemian.hellojava.spring.tx;

import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * We will use programmatic TransactionTemplate to manage tx instead of annotation.
 */
@Service
public class TemplateTxService {
    @Autowired
    TxSettingDAO settingDAO;

    @Autowired
    TxUserDAO userDAO;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    public void create(TestParam testParam, Setting setting, User user) {
        transactionTemplate.execute(txStatus -> {
            try {
                settingDAO.create(testParam, setting);
                userDAO.create(testParam, user);
                return null;
            } catch (RuntimeException e) {
                txStatus.setRollbackOnly();
                throw e;
            }
        });
    }

    public void create2(TestParam testParam, Setting setting, User user) {
        TransactionTemplate txTemplate = new TransactionTemplate(platformTransactionManager);
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txTemplate.execute(txStatus -> {
            try {
                settingDAO.create(testParam, setting);
                userDAO.create(testParam, user);
                return null;
            } catch (RuntimeException e) {
                txStatus.setRollbackOnly();
                throw e;
            }
        });
    }
}
