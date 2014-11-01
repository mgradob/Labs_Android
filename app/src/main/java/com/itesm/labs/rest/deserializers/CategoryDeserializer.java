package com.itesm.labs.rest.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.itesm.labs.rest.models.CategoryWrapper;

import java.lang.reflect.Type;

/**
 * Created by miguel on 25/10/14.
 */
public class CategoryDeserializer implements JsonDeserializer<CategoryWrapper> {

    @Override
    public CategoryWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonElement content = json.getAsJsonObject();

        return new Gson().fromJson(content, CategoryWrapper.class);
    }
}
