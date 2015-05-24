package com.karthikb351.vitinfo2.old.api.typeadapters;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.karthikb351.vitinfo2.old.api.models.ShareTokenResponse;
import com.karthikb351.vitinfo2.old.api.models.core.ShareToken;
import com.karthikb351.vitinfo2.old.api.models.core.Status;

import java.lang.reflect.Type;

/**
 * Created by karthikbalakrishnan on 26/03/15.
 */
public class ShareTokenResponseSerializer implements JsonDeserializer<ShareTokenResponse>
{
    @Override
    public ShareTokenResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {

        JsonObject obj = je.getAsJsonObject();

        ShareTokenResponse shareTokenResponse = new Gson().fromJson(obj, ShareTokenResponse.class);

        shareTokenResponse.setShare(new Gson().fromJson(obj.get("share"), ShareToken.class));

        shareTokenResponse.setStatus(new Gson().fromJson(obj.get("status"), Status.class));

        return shareTokenResponse;

    }
}
