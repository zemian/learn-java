package com.zemian.hellojava.spring.tx;

import com.zemian.hellojava.AppException;
import com.zemian.hellojava.data.domain.User;
import com.zemian.hellojava.data.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class TxUserDAO extends UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(TxUserDAO.class);

    public void create(TestParam testParam, User user) {
        try {
            // A direct connection from DS - This one we need to close it on our own!
            try (Connection conn = jdbc.getDataSource().getConnection()) {
                LOG.info("Before create: Using jdbc.getDataSource().conn={}, autoCommit={}", conn, conn.getAutoCommit());
            }

            // These two are going through Spring util to ensure Conn is associate with a tx manager
            jdbc.execute((ConnectionCallback<Object>) conn -> {
                LOG.info("Before create: Using jdbc.execute.conn={}, autoCommit={}", conn, conn.getAutoCommit());
                return null;
            });
            {
                // We can not close this connection since it's participating in tx! We just want to log it.
                Connection conn = DataSourceUtils.getConnection(jdbc.getDataSource());
                LOG.info("Before create: Using DataSourceUtils.getConnection={}, autoCommit={}", conn, conn.getAutoCommit());
            }

            super.create(user);
        } catch (Exception e) {
            throw new AppException(e);
        }

        if (testParam.isGenerateExceptionOnUserDAO()) {
            throw new AppException("Generating error on purose inside UserDAO");
        }
    }
}
