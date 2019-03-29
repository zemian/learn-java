package com.zemian.hellojava.data;

import com.zemian.hellojava.SpringTestBase;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = DBPropsDataConfig.class)
public class DbPropsDataConfigTest extends SpringTestBase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void dbProps() {
        assertThat(dataSource, instanceOf(DriverManagerDataSource.class));

        DriverManagerDataSource dmDs = (DriverManagerDataSource) dataSource;
        Properties dbProps = dmDs.getConnectionProperties();

        assertThat(dbProps.getProperty("ApplicationName"), is("hellojava"));
    }
}
