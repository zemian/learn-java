package zemian.hellojava;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class OSCommand {
    public static void main(String[] args) throws Exception {
        runTomcat(args);
    }
    
    private static String userDir = System.getProperty("user.home");

    public static void runTomcat(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(userDir + "/apps/apache-tomcat-8.5.32/bin/catalina.bat", "run");
        Map<String, String> env = pb.environment();
        env.put("JAVA_HOME", userDir + "/apps/jdk-11");
        env.put("CATALINA_HOME", userDir + "/apps/apache-tomcat-8.5.32");
        pb.redirectErrorStream(true);
        final Process p = pb.start();
        new Thread(() -> {
            try {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                // Ignore.
            }
        }).start();
        p.waitFor();
    }

    public static void processBuilder(String[] args) throws Exception {
        boolean useOutFileRedirect = true;

        System.out.println("Run java command");
        ProcessBuilder pb = new ProcessBuilder(userDir + "/apps/jdk-11/bin/java.exe", "-version");
        pb.redirectErrorStream(true);
        if (useOutFileRedirect)
            pb.redirectOutput(new File("out.txt"));
        Process p = pb.start();
        System.out.println("Process: " + p);
        p.waitFor();
        System.out.println("Process after waitFor: " + p);
        if (useOutFileRedirect) {
            System.out.println("out.txt: " + Files.readString(Paths.get("out.txt")));
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
    }

    public static void exec(String[] args) throws Exception {
        System.out.println("Run java command");
        Runtime.getRuntime().exec(userDir + "/apps/jdk-11/bin/java -version");
    }
}
