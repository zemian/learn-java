package com.zemian.hellojava.data.dao;

import com.zemian.hellojava.SpringTestBase;
import com.zemian.hellojava.data.domain.AuditLog;
import com.zemian.hellojava.data.CommonDataConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = CommonDataConfig.class)
public class AuditLogDAOTest extends SpringTestBase {
    @Autowired
    AuditLogDAO auditLogDAO;

    @Test
    public void crud() {
        AuditLog test = new AuditLog();
        try {
            test.setName("test");
            test.setValue("test");
            test.setCreatedDt(LocalDateTime.now());

            auditLogDAO.create(test);
            assertThat(test.getLogId(), greaterThanOrEqualTo(1));

            AuditLog test2 = auditLogDAO.get(test.getLogId());
            assertThat(test2.getLogId(), is(test.getLogId()));
            assertThat(test2.getName(), is("test"));
            assertThat(test2.getValue(), is("test"));
            assertThat(test2.getCreatedDt(), notNullValue());

            List<AuditLog> list = auditLogDAO.findAll();
            assertThat(list.size(), greaterThanOrEqualTo(1));
        } finally {
            auditLogDAO.delete(test.getLogId());
            try {
                auditLogDAO.get(test.getLogId());
                Assert.fail("There should not be record id=" + test.getLogId() + " after delete.");
            } catch (RuntimeException e) {
                // Expected.
            }
        }
    }
}