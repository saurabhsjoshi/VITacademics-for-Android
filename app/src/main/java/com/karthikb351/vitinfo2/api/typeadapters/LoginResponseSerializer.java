package com.karthikb351.vitinfo2.api.typeadapters;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.karthikb351.vitinfo2.api.models.LoginResponse;
import com.karthikb351.vitinfo2.api.models.core.Status;

import java.lang.reflect.Type;

/**
 * Created by karthikbalakrishnan on 26/03/15.
 */
public class LoginResponseSerializer implements JsonDeserializer<LoginResponse>
{
    @Override
    public LoginResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {

        JsonObject obj = je.getAsJsonObject();

        LoginResponse loginResponse = new Gson().fromJson(obj, LoginResponse.class);

        loginResponse.setStatus(new Gson().fromJson(obj.get("status"), Status.class));

        return loginResponse;

    }
}
