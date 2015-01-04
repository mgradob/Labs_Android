package com.itesm.labs.rest.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.itesm.labs.rest.models.UserWrapper;

import java.lang.reflect.Type;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public class UserDeserializer implements JsonDeserializer<UserWrapper> {
    @Override
    public UserWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonElement content = json.getAsJsonObject();

        return new Gson().fromJson(content, UserWrapper.class);
    }
}
