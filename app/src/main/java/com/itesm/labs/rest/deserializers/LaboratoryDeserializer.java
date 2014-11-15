package com.itesm.labs.rest.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.models.LaboratoryWrapper;

import java.lang.reflect.Type;

/**
 * Created by mgradob on 11/14/14.
 */
public class LaboratoryDeserializer implements JsonDeserializer<LaboratoryWrapper> {

    @Override
    public LaboratoryWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonElement content = json.getAsJsonObject();

        return new Gson().fromJson(content, LaboratoryWrapper.class);
    }
}
