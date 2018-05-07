package com.patis.wms.android.service.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by danya on 11.11.2017.
 */

public class DateAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {


    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return dateTimeFormat.parse(json.getAsString());
        } catch (ParseException e) {
            try{
                return dateFormat.parse(json.getAsString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(dateTimeFormat.format(src));
    }
}
