package com.example.app2048;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

    public void JsonGenerator() {
    }

    public JSONObject CreateObject(String position) {
        JSONObject object = new JSONObject();
        try {
            object.put("position", position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}