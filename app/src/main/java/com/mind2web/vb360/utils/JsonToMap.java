package com.mind2web.vb360.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonToMap {
    public static void main(String[] args) {
        String jsonString = "{\n" +
                "  \"sid\": \"CA01aac9a28f72f2ba096c4f05ff745512\",\n" +
                "  \"account_sid\": \"AC757d10993413d36e7da7b699ac67fee0\",\n" +
                "  \"service_sid\": \"MG39164e29dc9c7889d42ae4d495e751d4\",\n" +
                "  \"date_created\": \"2024-12-21T23:42:28Z\",\n" +
                "  \"identities\": [\n" +
                "    \"kevinemp_647\"\n" +
                "  ],\n" +
                "  \"tags\": [],\n" +
                "  \"segments\": [],\n" +
                "  \"priority\": \"high\",\n" +
                "  \"ttl\": 2419200,\n" +
                "  \"title\": \"test\",\n" +
                "  \"body\": \"Hello Bob\",\n" +
                "  \"sound\": null,\n" +
                "  \"action\": null,\n" +
                "  \"data\": null,\n" +
                "  \"apn\": null,\n" +
                "  \"fcm\": null,\n" +
                "  \"gcm\": null,\n" +
                "  \"sms\": null,\n" +
                "  \"facebook_messenger\": null,\n" +
                "  \"alexa\": null\n" +
                "}";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Map<String, Object> map = jsonToMap(jsonObject);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> jsonToMap(JSONObject jsonObject) throws Exception {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keys = jsonObject.keys(); // Use keys() to get an iterator
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);

            if (value instanceof JSONArray) {
                map.put(key, jsonArrayToList((JSONArray) value));
            } else if (value instanceof JSONObject) {
                map.put(key, jsonToMap((JSONObject) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    private static List<Object> jsonArrayToList(JSONArray jsonArray) throws Exception {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);

            if (value instanceof JSONArray) {
                list.add(jsonArrayToList((JSONArray) value));
            } else if (value instanceof JSONObject) {
                list.add(jsonToMap((JSONObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }
}
