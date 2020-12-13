package com.tripleM.sendmeal_lab01.room;

import android.util.Pair;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Pair<String,String>> fromString(String value) {
        Type listType = new TypeToken<List<Pair<String,String>>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Pair<String,String>> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}