package me.lesh.commands;

import me.lesh.Main;
import me.lesh.material.Green;
import me.lesh.material.Red;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.IOException;

public class Invite extends ListenerAdapter{
    public void onMessageReceived(MessageReceivedEvent e) {
        Message m = e.getMessage();
        EmbedBuilder eB = new EmbedBuilder();
        if(m.getContentRaw().startsWith("/addme ")){
            Rcon rcon = null;
            String[] split = e.getMessage().getContentRaw().split("\\s+",2);
            try { rcon = new Rcon(Main.CONFIG.getHostIp(), Main.CONFIG.getRconPort(), Main.CONFIG.getRconPw().getBytes()); }
            catch (IOException e1) { e1.printStackTrace(); }
            catch (AuthenticationException e1) { e1.printStackTrace(); }
            try { rcon.command("whitelist add " + split[1]); rcon.command("whitelist reload"); }
            catch (IOException e1) { e1.printStackTrace(); }

            // Add user to whitelist
            eB.addField("User " + e.getMessage().getMember().getEffectiveName() + " added " + split[1],"User: " + e.getMessage().getMember().getEffectiveName() + " hat den Minecraft User " + split[1] + " dem Server hinzugefügt",false);
            eB.setColor(Green.g700);
            e.getChannel().sendMessage(eB.build()).queue();
            return;
        }
        if(m.getContentRaw().startsWith("/remove ") && e.getMember().isOwner()){
            Rcon rcon = null;
            String[] split = e.getMessage().getContentRaw().split("\\s+",2);
            try { rcon = new Rcon(Main.CONFIG.getHostIp(), Main.CONFIG.getRconPort(), Main.CONFIG.getRconPw().getBytes()); }
            catch (IOException e1) { e1.printStackTrace(); }
            catch (AuthenticationException e1) { e1.printStackTrace(); }
            try { rcon.command("whitelist remove " + split[1]); rcon.command("whitelist reload"); }
            catch (IOException e1) { e1.printStackTrace(); }


            // Remove user from whitelist
            eB.addField("User " + e.getMessage().getMember().getEffectiveName() + " removed " + split[1],"User: " + e.getMessage().getMember().getEffectiveName() + " hat den Minecraft User " + split[1] + " dem Server entfernt",false);
            eB.setColor(Red.r600);
            e.getChannel().sendMessage(eB.build()).queue();
            return;
        }
    }
}
