package me.lesh.rconinvite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.lesh.rconinvite.utils.ConfigSetup;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.utils.JDALogger;
import me.lesh.rconinvite.commands.*;
import org.slf4j.Logger;

import java.io.*;

public class Main {

    /*
            MADE BY LESH
     */

    public static JDA jda;
    public static JDABuilder jdaB = new JDABuilder(AccountType.BOT);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    public static String fileName = "config.json";

    public static ConfigSetup CONFIG = new ConfigSetup();
    public static Logger LOG = JDALogger.getLog("RconInvite");

    public static void main(String[] args) {
        LOG.info("Starting RconInvite+ ...");
        setup();
        try { jda = jdaB.setToken(CONFIG.getToken()).setGame(Game.listening("/addme <MC-Name>")).buildBlocking(); }
        catch(Exception e){ LOG.warn("There was an error during launching - " + e); }

        jda.addEventListener(new Whitelist());
        jda.addEventListener(new Op());

        LOG.info("Ready to use ... Have fun");
    }

    public static void setup(){
        try{
            File file = new File(fileName);
            if(!file.exists()){
                if(file.createNewFile()){
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(GSON.toJson(CONFIG));
                    writer.close();
                    LOG.info("Succesful created CONFIG file");
                    System.exit(0);
                } else {
                    LOG.error("There is no prefix. Set your prefix for your commands");
                    return;
                }
                LOG.info("Config has been loaded ... ");
            }else {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                CONFIG = GSON.fromJson(reader, ConfigSetup.class);
                if (CONFIG.getToken() == null || CONFIG.getToken().isEmpty()) {
                    LOG.error("There is no token. Set your Token for your bot");
                    return;
                }
                if(CONFIG.getHostIp() == null || CONFIG.getHostIp().isEmpty()) {
                    LOG.error("There is no hostIp. Set the servers hostIp");
                    return;
                }
                if (CONFIG.getRconPw() == null) {
                    LOG.error("There is no rconPassword. Set your rconPassword for your server");
                    return;
                }
                if ((CONFIG.getRconPort() == 0)) {
                    LOG.error("There is no port. Set your port for your server");
                    return;
                }

                LOG.info("Config has been loaded ... ");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
