package com.zemian.toolbox.app;

import com.zemian.toolbox.support.CmdOpts;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Zemian Deng on 2018
 */
public class FindJar implements Command {
    public static void printExitHelp() {
        System.out.println("Find a entry item (class or resource) in one or more jar files.\n" +
                "\n" +
                "Usage: FindJar <entryName> [dirOfJars ...]\n" +
                "  If no dirOfJars is given, it defaults to '.'\n" +
                "");
        System.exit(1);
    }

    @Override
    public void run(CmdOpts opts) throws Exception {
        if (opts.getArgsSize() < 1 || opts.hasOpt("help")) {
            printExitHelp();
        }

        String entry = opts.getArgOrError(0, "Invalid entry name.");
        if (entry.indexOf(".") >= 0) {
            entry = entry.replaceAll("\\.", "/");
        }
        //System.out.println("debug: Searching " + entry);
        List<String> dirs = opts.getArgs().subList(1, opts.getArgsSize());
        if (dirs.size() == 0) {
            dirs.add(".");
        }

        for (String dir : dirs) {
            findJar(new File(dir), entry);
        }
    }

    private void findJar(File dir, String search) throws Exception {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findJar(file, search);
                } else if (file.getName().endsWith(".jar")) {
                    findJar(new JarFile(file), search);
                }
            }
        }
    }

    private void findJar(JarFile jarFile, String search) throws Exception {
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (jarEntry.getName().indexOf(search) >= 0) {
                System.out.println(jarFile.getName() + ": " + jarEntry.getName());
            }
        }
    }
}
