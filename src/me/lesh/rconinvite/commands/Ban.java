package me.lesh.rconinvite.commands;

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

public class Ban extends ListenerAdapter{

    private String banReason = "NaN";

    public void onMessageReceived(MessageReceivedEvent e) {
        Message m = e.getMessage();
        EmbedBuilder eB = new EmbedBuilder();
        if(m.getContentRaw().startsWith("/ban ")) {
            Rcon rcon = null;
            String[] split = e.getMessage().getContentRaw().split("\\s+", 2);
            try {
                rcon = new Rcon(Main.CONFIG.getHostIp(), Main.CONFIG.getRconPort(), Main.CONFIG.getRconPw().getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }
            if(split[1] == ""){
                eB.addField("Fehler: Missing argument", "Um `/Ban` nutzen zu können benötigt es mindestens einen Spielernamen -> `/Ban Notch`", false);
                e.getChannel().sendMessage(eB.build()).queue();
                return;
            }
            if (split.length > 1) {
                try {
                    rcon.command("ban " + split[1]);
                    rcon.command("tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" "+ split[1] +" \",\"color\":\"white\",\"bold\":false},{\"text\":\" wurde vom Server gebannt!\",\"color\":\"dark_red\",\"bold\":false}]");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(split.length > 2) {
                try {
                    rcon.command("ban " + split[1] + " " + split[2]);
                    rcon.command("tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" "+ split[1] +" \",\"color\":\"white\",\"bold\":false},{\"text\":\" wurde vom Server gebannt! Grund: " + split[2] + "\",\"color\":\"dark_red\",\"bold\":false}]");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            // Add user to whitelist
            eB.addField("User " + split[1] + " wurde gebannt",e.getAuthor().getName() + " hat " + split[1] + " gebannt.\n Banngrund: " + banReason,false);
            eB.setColor(Red.r900);
            e.getChannel().sendMessage(eB.build()).queue();
            return;
        }
    }

}
