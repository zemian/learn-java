package com.zemian.hellojava.spring.multidb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class MultiTxService {
    private static final Logger LOG = LoggerFactory.getLogger(MultiTxService.class);

    @Autowired
    @Qualifier("jdbcTemplate")
    JdbcTemplate jdbc;

    @Autowired
    @Qualifier("jdbcTemplateDB2")
    JdbcTemplate jdbcDB2;

    @Autowired
    @Qualifier("platformTransactionManager")
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    @Qualifier("platformTransactionManagerDB2")
    PlatformTransactionManager platformTransactionManagerDB2;

    // NOTE You can not use two @Transactional to make declarative tx!
    //@Transactional("platformTransactionManager")
    //@Transactional("dataSourceTransactionManagerDB2")
    public void create() {
        TransactionTemplate txTempplate = new TransactionTemplate(platformTransactionManager);
        TransactionTemplate txTempplateDB2 = new TransactionTemplate(platformTransactionManagerDB2);
        txTempplate.execute(txStatus -> {
            return txTempplateDB2.execute(txStatusDB2 -> {
                jdbc.execute("INSERT INTO settings(category, name, value)" +
                        " VALUES('TEST', 'MultiTxService', 'foo')");
                LOG.info("DB1: Inserted settings record");

                jdbcDB2.execute("INSERT INTO audit_logs(name, value)" +
                        " VALUES('MultiTxService', 'foo')");
                LOG.info("DB2: Inserted audit_logs record");

                return null;
            });
        });
    }
}
