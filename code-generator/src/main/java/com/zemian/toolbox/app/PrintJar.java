package com.zemian.toolbox.app;

import com.zemian.toolbox.support.CmdOpts;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Zemian Deng on 2017
 */
public class PrintJar implements Command {
    public static void printExitHelp() {
        System.out.println("Print a text entry item in a jar file.\n" +
                "\n" +
                "Usage: PrintJar <jarFile> [entryName]\n" +
                "");
        System.exit(1);
    }

    @Override
    public void run(CmdOpts opts) throws Exception {
        if (opts.getArgsSize() < 1 || opts.hasOpt("help")) {
            printExitHelp();
        }

        String fileName = opts.getArgOrError(0, "Invalid jar filename.");
        String entry = "META-INF/MANIFEST.MF";
        if (opts.getArgsSize() >= 2) {
            entry = opts.getArg(1);
        }

        JarFile jarFile = new JarFile(fileName);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (entry.equals(jarEntry.getName())) {
                try (InputStream ins = jarFile.getInputStream(jarEntry)) {
                    String text = IOUtils.toString(ins, "UTF-8");
                    System.out.println(text);
                    break;
                }
            }
        }
    }
}
