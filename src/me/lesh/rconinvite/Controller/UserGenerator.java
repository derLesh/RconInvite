package me.lesh.rconinvite.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public class UserGenerator {

    public final static String fileName = "userlist.json";
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

}
