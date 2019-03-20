package com.zemian.hellojava.datetime;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NewDateTest {
    @Test
    public void date() {
        Instant now = Instant.now();
        assertThat(now.getEpochSecond(), greaterThan(0L));

        Instant epoch = Instant.ofEpochSecond(0);
        assertThat(epoch.getEpochSecond(), is(0L));

        LocalDateTime dt = LocalDateTime.now();
        assertThat(dt.getDayOfMonth(), greaterThan(0));

        Duration dur = Duration.between(now, Instant.now());
        assertThat(dur.getNano(), greaterThanOrEqualTo(0));
    }
}
