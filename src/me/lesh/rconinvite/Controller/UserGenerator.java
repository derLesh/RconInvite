package me.lesh.rconinvite.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import me.lesh.rconinvite.utils.UserJson;

public class UserGenerator {

    public final static String fileName = "userlist.json";
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    
    private static List<UserJson> userlist = loadUserList();
    
    
    public static void addUser(String discordName, String discordID, String minecraftName, boolean hasInviteLeft) {
    	userlist.add(new UserJson(discordName, discordID, minecraftName, hasInviteLeft));
    	saveUserlist();
    }

	public static void removeUser(String discordName, String discordID, String minecraftName, boolean hasInviteLeft) {
    	userlist.remove(new UserJson(discordName, discordID, minecraftName, hasInviteLeft));
    	saveUserlist();
    }
    
    public static List<UserJson> loadUserList() {
    	List<UserJson> cached = new ArrayList<UserJson>();
    	
    	try {
    		Type listType = new TypeToken<List<UserJson>>() {}.getType();
    		
            List<UserJson> readFromJson = GSON.fromJson(new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8), listType);
            
            if(readFromJson != null) {
            	 for(int i = 0; i < readFromJson.size(); i++) {
                 	cached.add((UserJson)readFromJson.get(i));
                 }
            }
            
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
        
		return cached;
	}
	
	private static void saveUserlist() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.write(GSON.toJson(userlist));
            writer.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
