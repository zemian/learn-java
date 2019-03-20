package zemian.javastarter.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class WaitNotifyWorker implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(WaitNotifyWorker.class);
    private AtomicBoolean running = new AtomicBoolean(false);
    private Object waitLock = new Object();

    @Override
    public void run() {
        start();
    }

    public void start() {
        running.set(true);
        while(running.get()) {
            doWork();
            blockAndWait();
        }
    }

    public abstract void doWork();

    public void blockAndWait() {
        LOG.info("Placing thread into wait mode.");
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
                LOG.error("Failed in wait mode.", e);
            }
        }
    }

    public void stop() {
        synchronized (waitLock) {
            waitLock.notify();
        }
        running.set(false);
    }

    public boolean isRunning() {
        return running.get();
    }
}
