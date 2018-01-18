package me.lesh.rconinvite.commands;

import me.lesh.material.Green;
import me.lesh.material.Red;
import me.lesh.material.Yellow;
import me.lesh.rconinvite.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.IOException;

public class Op extends ListenerAdapter{

    String rconList = null;

    public void onMessageReceived(MessageReceivedEvent e) {
        Message m = e.getMessage();
        EmbedBuilder eB = new EmbedBuilder();
        if(m.getContentRaw().startsWith("/op")){
            Rcon rcon = null;
            String[] split = e.getMessage().getContentRaw().split("\\s+", 1);
            try { rcon = new Rcon(Main.CONFIG.getHostIp(), Main.CONFIG.getRconPort(), Main.CONFIG.getRconPw().getBytes()); }
            catch (IOException e1) { e1.printStackTrace(); }
            catch (AuthenticationException e1) { e1.printStackTrace(); }
            try { rconList = rcon.command("op " + split[1]); }
            catch (IOException e1) { e1.printStackTrace(); }

            // Add user to whitelist
            eB.addField("Whitelist für " + Main.CONFIG.getHostIp(),"Derzeit whitelisted User: \n" + rconList.toString(),false);
            eB.setColor(Yellow.y600);
            e.getChannel().sendMessage(eB.build()).queue();
            return;
        }
    }
}
