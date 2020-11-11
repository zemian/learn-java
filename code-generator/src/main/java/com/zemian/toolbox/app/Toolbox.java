package com.zemian.toolbox.app;

import ch.qos.logback.classic.Level;
import com.zemian.toolbox.support.CmdOpts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper to invoke a Command instance. This entry class support the following global options:
 *   --help           Print help and exit
 *   --logLevel=INFO  Change logging level
 *
 */
public class Toolbox {
    private static final Logger LOG = LoggerFactory.getLogger(Toolbox.class);

    public static void printExitHelp() {
        System.out.println("Usage: Toolbox <commandClassName> [options]");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        CmdOpts opts = new CmdOpts(args);

        if (opts.getArgsSize() < 1 ||
                (opts.getArgsSize() == 0 && opts.hasOpt("help"))) {
            printExitHelp();
        }

        if (opts.hasOpt("logLevel")) {
            String logLevel = opts.getOpt("logLevel");
            ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(
                    ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.valueOf(logLevel));
            LOG.debug("Log level changed to {}", logLevel);
        }

        String commandClassName = opts.getArgOrError(0, "Invalid command class name.");

        // Default to use our package, so user can just specify simple className.
        if (commandClassName.indexOf(".") < 0) {
            commandClassName = "com.zemian.toolbox.app." + commandClassName;
        }

        // Create new command instance
        LOG.debug("Creating new instance of {}", commandClassName);
        Class<?> clazz = Class.forName(commandClassName);
        if (!Command.class.isAssignableFrom(clazz)) {
            System.out.println("ERROR: CommandClassName is not an instance of " + Command.class);
            printExitHelp();
        }
        Command cmd = (Command) clazz.newInstance();

        // Remove first args as new options to pass to cmd instance
        opts.getArgs().remove(0);

        LOG.trace("Using opts={}", opts.getOpts());
        LOG.debug("Running cmd {} with args.size={}", cmd, opts.getArgsSize());
        cmd.run(opts);
    }
}
