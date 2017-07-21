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


public class FloatDefault0Adapter implements JsonSerializer<Float>, JsonDeserializer<Float> {
    @Override
    public Float deserialize(JsonElement json, Type typeOfT, 
                            JsonDeserializationContext context) 
                             throws JsonParseException {
        try {
        	//转换失败，代表是String类型，所以直接返回0
        	Float i=Float.parseFloat(json.getAsString());
        } catch (Exception ignore){
        	return (float)0;
        }
        try {
            return json.getAsFloat();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
