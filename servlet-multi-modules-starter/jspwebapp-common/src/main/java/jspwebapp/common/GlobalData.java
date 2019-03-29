package jspwebapp.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GlobalData {
    private static GlobalData INSTANCE = new GlobalData();
    private Map<String, Object> dataMap = Collections.synchronizedMap(new HashMap<>());

    private GlobalData() {}

    public static GlobalData getInstance() {
        return INSTANCE;
    }

    public void putData(String name, Object data) {
        dataMap.put(name, data);
    }

    public <T> T getData(String name) {
        return (T) dataMap.get(name);
    }

    public Set<String> getNames() {
        return dataMap.keySet();
    }

    public int getSize() {
        return dataMap.size();
    }
}
