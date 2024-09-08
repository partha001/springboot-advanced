package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextHolder {

    public static Map<String,Object> map = new ConcurrentHashMap<>();
}
