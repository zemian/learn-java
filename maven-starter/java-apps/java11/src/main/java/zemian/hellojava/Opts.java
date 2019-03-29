package zemian.hellojava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A very simple command line arguments and options parser.
 */
public class Opts {
    private List<String> args = new ArrayList<>();
    private Map<String, Object> opts = new HashMap<>();

    public Opts(String[] args) {
        for (String arg : args) {
            String[] kv = arg.split("=");
            if (kv[0].startsWith("-")) {
                int start = 1;
                if (kv[0].startsWith("--"))
                    start = 2;
                String key = kv[0].substring(start).trim();
                Object val = true;
                if (kv.length > 1) {
                    val = kv[1];
                } else if (arg.endsWith("=")) {
                    val = "";
                }
                opts.put(key, val);
            } else {
                this.args.add(arg);
            }
        }
    }

    public List<String> getArgs() {
        return args;
    }

    public Map<String, Object> getOpts() {
        return opts;
    }

    // == Convenient getters
    public String getOpt(String name) {
        return (String) opts.get(name);
    }

    public String getOpt(String name, String def) {
        return (String) opts.getOrDefault(name, def);
    }

    public boolean hasOpt(String name) {
        return opts.containsKey(name);
    }
}
