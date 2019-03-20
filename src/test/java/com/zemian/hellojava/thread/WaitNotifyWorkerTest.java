package com.zemian.hellojava.thread;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class WaitNotifyWorkerTest {
    public static class MyTinyServer extends WaitNotifyWorker {
        private static final Logger LOG = LoggerFactory.getLogger(MyTinyServer.class);
        private String status = "NEW";

        public String getStatus() {
            return status;
        }

        @Override
        public void doWork() {
            LOG.info("Do what a server must do...");
            status = "WORKING";
        }
    }

    @Test
    public void serverStartAndWait() throws Exception {
        MyTinyServer server = new MyTinyServer();
        Thread serverThread = new Thread(server);

        assertThat(server.isRunning(), is(false));
        assertThat(server.getStatus(), is("NEW"));

        serverThread.start();

        // Let it run for a little bit
        Thread.sleep(900L);

        assertThat(server.isRunning(), is(true));
        assertThat(server.getStatus(), is("WORKING"));

        // Wait some more time...
        Thread.sleep(2000L);

        // Stop it
        server.stop();
        assertThat(server.isRunning(), is(false));
    }
}