package com.itesm.labs.rest.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.itesm.labs.rest.models.ComponentWrapper;

import java.lang.reflect.Type;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public class ComponentDeserializer implements JsonDeserializer<ComponentWrapper> {

    @Override
    public ComponentWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonElement content = json.getAsJsonObject();

        return new Gson().fromJson(content, ComponentWrapper.class);
    }

}
