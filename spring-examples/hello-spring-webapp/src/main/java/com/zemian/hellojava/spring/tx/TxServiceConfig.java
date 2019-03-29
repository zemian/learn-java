package com.zemian.hellojava.spring.tx;

import com.zemian.hellojava.service.CommonServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Profile("demo-spring-tx")
@Configuration
@Import(CommonServiceConfig.class)
public class TxServiceConfig {

    @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

    @Bean
    TxService txService() {
        return new TxService();
    }

    @Bean
    TemplateTxService templateTxService() {
        return new TemplateTxService();
    }

    @Bean
    NoTxService serviceWithNoTx() {
        return new NoTxService();
    }

    @Bean
    TxSettingDAO txSettingDAO() {
        return new TxSettingDAO();
    }

    @Bean
    TxUserDAO txUserDAO() {
        return new TxUserDAO();
    }
}
