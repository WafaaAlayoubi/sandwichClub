package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        String mainName = null;
        String sandwichImg = null;
        JSONArray alsoKnownAs = null;
        JSONArray ingredients = null;

        JSONObject reader = null;
        JSONObject Name = null;
        String placeOfOrigin = null;
        String description = null;

        try {
            reader = new JSONObject(json);
            Name  = reader.getJSONObject("name");
            mainName = Name.getString("mainName");
            sandwichImg = reader.getString("image");
            alsoKnownAs = Name.getJSONArray("alsoKnownAs");
            ingredients = reader.getJSONArray("ingredients");
            placeOfOrigin  = reader.getString("placeOfOrigin");
            description  = reader.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Sandwich sandwichObj =  new Sandwich(mainName,covertJsoneTOList(alsoKnownAs),placeOfOrigin,description,sandwichImg,covertJsoneTOList(ingredients));

        return sandwichObj;
    }

    public static List<String> covertJsoneTOList(JSONArray jsonArray) throws JSONException {

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {

            String innerObj = jsonArray.getString(i);
            list.add(innerObj);

        }
        return list;
    }
}
