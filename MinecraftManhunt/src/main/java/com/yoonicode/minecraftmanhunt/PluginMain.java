package com.yoonicode.minecraftmanhunt;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getScoreboardManager;

enum ManhuntTeam {
    HUNTER,
    RUNNER,
    SPECTATOR
}

public class PluginMain extends JavaPlugin {

    // Plugin not working? Maybe try downgrading to java version 7

    public ArrayList<String> hunters = new ArrayList<String>();
    public ArrayList<String> runners = new ArrayList<String>();
    public ArrayList<String> spectators = new ArrayList<String>();
    public HashMap<String, String> targets = new HashMap<String, String>();
    public Team huntersTeam;
    public Team runnersTeam;
    public Team spectatorsTeam;
    public Logger logger;
    public DiscordManager discord;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("Minecraft Manhunt plugin enabled!");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PluginListener(this), this);

        PluginCommands commands = new PluginCommands(this);
        for(String command : PluginCommands.registeredCommands){
            this.getCommand(command).setExecutor(commands);
        }

        ScoreboardManager scoreboardManager = getScoreboardManager();
        Scoreboard board = scoreboardManager.getMainScoreboard();
        huntersTeam = board.getTeam("hunters");
        runnersTeam = board.getTeam("speedrunners");
        spectatorsTeam = board.getTeam("spectators");
        if(huntersTeam == null){
            huntersTeam = board.registerNewTeam("hunters");
            huntersTeam.setColor(ChatColor.RED);
        }
        if(runnersTeam == null){
            runnersTeam = board.registerNewTeam("speedrunners");
            runnersTeam.setColor(ChatColor.GREEN);
        }
        if(spectatorsTeam == null){
            spectatorsTeam = board.registerNewTeam("spectators");
            spectatorsTeam.setColor(ChatColor.AQUA);
        }

        discord = new DiscordManager(this);
    }

    @Override
    public void onDisable() {
        logger.info("Minecraft Manhunt plugin disabled!");
    }

}
