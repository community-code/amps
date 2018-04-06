package com.communitycode.amps.main.settings;

import android.content.Context;

import com.communitycode.amps.main.battery.BatteryMethodInterface;
import com.communitycode.amps.main.battery.OfficialBatteryMethod;
import com.communitycode.amps.main.battery.UnofficialBatteryMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

public class BatteryMethodPickler {
    private static Class<BatteryMethodInterface>[] x = new Class[]{OfficialBatteryMethod.class, UnofficialBatteryMethod.class};

    public static BatteryMethodInterface fromJson(String json, Context mCtx) {
        if (json == null) {
            return null;
        }

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(OfficialBatteryMethod.class, new OfficialBatteryApiInstanceCreator(mCtx))
                    .create();
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(json).getAsJsonObject();
            int class_index = obj.getAsJsonPrimitive("type").getAsInt();
            JsonElement data = obj.getAsJsonObject("data");
            return gson.fromJson(data, x[class_index]);
        }
        catch (Exception ignored) {
        }
        return null;
    }

    public static String toJson(BatteryMethodInterface obj) {
        for (int i = 0; i < x.length; i ++) {
            if (x[i].isInstance(obj)) {
                Gson gson = new Gson();
                return gson.toJson(new Wrapper(i, obj));
            }
        }
        return null;
    }

    private static class Wrapper {
        int type;
        BatteryMethodInterface data;

        public Wrapper(int type, BatteryMethodInterface data) {
            this.type = type;
            this.data = data;
        }
    }

    private static class OfficialBatteryApiInstanceCreator implements InstanceCreator<OfficialBatteryMethod> {
        private Context mCtx;

        OfficialBatteryApiInstanceCreator(Context context) {
            mCtx = context;
        }


        @Override
        public OfficialBatteryMethod createInstance(Type type) {
            return new OfficialBatteryMethod(mCtx);
        }
    }
}
