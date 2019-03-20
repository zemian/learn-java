package com.zemian.hellojava.thread;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {

    private Set<String> uuidSet = Collections.synchronizedSet(new HashSet<>());
    public class Work implements Runnable {
        @Override
        public void run() {
            String uuid = UUID.randomUUID().toString();
            uuidSet.add(uuid);
        }
    }

    @Test(timeout = 5000L)
    public void test() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4000; i++) {
            pool.submit(new Work());
        }

        while (uuidSet.size() < 4000) {
            Thread.sleep(333L);
        }
        assertThat(uuidSet.size(), is(4000));
    }
}
