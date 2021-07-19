package com.apps.developerblog.app.ws.mobileappws.utils;

import java.util.HashMap;
import java.util.Map;

public class Response {

    public static Map<String, String> message(String key, String message) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(key, message);
        return messageMap;
    }
}
