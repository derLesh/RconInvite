package me.lesh.rconinvite.utils;

import java.util.List;

public class UserJson {
    
    public UserJson(String discordName, String discordID, String minecraftName, boolean hasInviteLeft) {
		this.discordName = discordName;
		this.discordID = discordID;
		this.minecraftName = minecraftName;
		this.hasInviteLeft = hasInviteLeft;
	}
	
	private String discordName;
    private String discordID;
    private String minecraftName;
    private boolean hasInviteLeft;
    
	public String getDiscordName() {
		return discordName;
	}
	public String getDiscordID() {
		return discordID;
	}
	public String getMinecraftName() {
		return minecraftName;
	}
	public boolean isHasInviteLeft() {
		return hasInviteLeft;
	}
    
}
