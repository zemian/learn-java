package com.zemian.hellojava.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemian.hellojava.SpringTestBase;
import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.data.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = CommonServiceConfig.class)
public class ObjectMapperTest extends SpringTestBase {
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testJsonSerialization() throws Exception {
        Setting setting = new Setting("TEST", "foo", "bar");
        String json = objectMapper.writeValueAsString(setting);
        assertThat(json, is("{\"category\":\"TEST\",\"name\":\"foo\",\"value\":\"bar\"}"));

        User user = new User("test", "pass");
        user.setCreatedDt(LocalDateTime.of(2018, 1, 8, 3, 0, 0));
        json = objectMapper.writeValueAsString(user);
        assertThat(json, is("{\"username\":\"test\",\"password\":\"pass\",\"fullName\":\"User test\",\"admin\":false,\"deleted\":false,\"createdDt\":\"2018-01-08T03:00:00\"}"));
    }
}
