package me.lesh.rconinvite.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.lesh.material.LightGreen;
import me.lesh.rconinvite.Main;
import me.lesh.material.Red;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Invite extends ListenerAdapter{

    private String addedUser = null;
    private String removeUser = null;

    public void onMessageReceived(MessageReceivedEvent e) {
        Message m = e.getMessage();
        EmbedBuilder eB = new EmbedBuilder();
        if(m.getContentRaw().startsWith("/addme ")){
            Rcon rcon = null;
            String[] split = e.getMessage().getContentRaw().split("\\s+",2);
            addedUser = split[1];
            try { rcon = new Rcon(Main.CONFIG.getHostIp(), Main.CONFIG.getRconPort(), Main.CONFIG.getRconPw().getBytes()); }
            catch (IOException e1) { e1.printStackTrace(); }
            catch (AuthenticationException e1) { e1.printStackTrace(); }
            try { rcon.command("whitelist add " + split[1]); rcon.command("whitelist reload"); rcon.command("tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" "+ addedUser +" \",\"color\":\"white\",\"bold\":false},{\"text\":\" wurde der Whitelist hinzugefügt!\",\"color\":\"green\",\"bold\":false}]");}

            try (Writer writer = new FileWriter("userlist.json")) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(, writer);
            }

            catch (IOException e1) { e1.printStackTrace(); }



            // Add user to whitelist
            eB.addField("User " + e.getMessage().getMember().getEffectiveName() + " added " + split[1],"User: " + e.getMessage().getMember().getEffectiveName() + " hat den Minecraft User " + split[1] + " dem Server hinzugefügt",false);
            eB.setColor(LightGreen.lg900);
            e.getChannel().sendMessage(eB.build()).queue();
            return;
        }
        if(m.getContentRaw().startsWith("/remove ") && e.getMember().isOwner()){
            Rcon rcon = null;
            String[] split = e.getMessage().getContentRaw().split("\\s+",2);
            removeUser = split[1];
            try { rcon = new Rcon(Main.CONFIG.getHostIp(), Main.CONFIG.getRconPort(), Main.CONFIG.getRconPw().getBytes()); }
            catch (IOException e1) { e1.printStackTrace(); }
            catch (AuthenticationException e1) { e1.printStackTrace(); }
            try { rcon.command("whitelist remove " + split[1]); rcon.command("whitelist reload"); rcon.command("tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" "+ removeUser +" \",\"color\":\"white\",\"bold\":false},{\"text\":\" wurde von der Whitelist entfernt!\",\"color\":\"red\",\"bold\":false}]"); }
            catch (IOException e1) { e1.printStackTrace(); }

            // Remove user from whitelist
            eB.addField("User " + e.getMessage().getMember().getEffectiveName() + " removed " + split[1],"User: " + e.getMessage().getMember().getEffectiveName() + " hat den Minecraft User " + split[1] + " dem Server entfernt",false);
            eB.setColor(Red.r700);
            e.getChannel().sendMessage(eB.build()).queue();
            return;
        }

    }
}
