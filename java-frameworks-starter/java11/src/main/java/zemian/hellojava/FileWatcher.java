package zemian.hellojava;

import java.nio.file.*;

public class FileWatcher {
    public static void main(String[] args) throws Exception {
        new FileWatcher("watchtest").run();
    }

    private Path dir;
    public FileWatcher(String dir) {
        this.dir = Paths.get(dir);
    }

    private void run() throws Exception {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            System.out.println("Waiting for event...");
            WatchKey wkey = watcher.take();
            System.out.println("Got an event: " + wkey);
            Watchable w = wkey.watchable();
            System.out.println("Watchable object: " + w);
            System.out.println("Watchable object type: " + w.getClass());
            for (WatchEvent<?> event : wkey.pollEvents()) {
                System.out.println("Event context: " + event.context());
                System.out.println("Event context type: " + event.context().getClass());
            }
            wkey.reset();
        }
    }
}
