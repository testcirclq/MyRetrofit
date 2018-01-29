package com.yanxuwen.retrofit.JsonSerializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonElement json, Type typeOfT, 
                            JsonDeserializationContext context) 
                             throws JsonParseException {
        try {
        	//转换失败，代表是String类型，所以直接返回0
        	double i=Double.parseDouble(json.getAsString());
        } catch (Exception ignore){
        	return (double) 0;
        }
        try {
            return json.getAsDouble();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
