package com.zemian.hellojava.support;

import java.util.HashMap;
import java.util.Map;

public class JavaUtils {
    public static Map<String, Object> map(Object ... pairs) {
        Map<String, Object> ret = new HashMap<>();
        for (int i = 0; i < pairs.length; i+=2) {
            ret.put(pairs[i].toString(), pairs[i+1]);
        }
        return ret;
    }
}
