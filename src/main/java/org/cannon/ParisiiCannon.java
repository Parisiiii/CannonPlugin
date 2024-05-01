package org.cannon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cannon.commands.CannonCommand;
import org.cannon.events.CannonInteractListener;
import org.cannon.manager.CannonManager;

public class ParisiiCannon extends JavaPlugin {
    private CannonManager cannonManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        cannonManager = new CannonManager(this, getConfig());
        initialize();
    }

    public void initialize() {
        cannonManager.loadCannons();
        getCommand("cannon").setExecutor(new CannonCommand(cannonManager));
        getServer().getPluginManager().registerEvents(new CannonInteractListener(cannonManager, getConfig()), this);
    }

    public void debug(String message) {
        Bukkit.getConsoleSender().sendMessage("§a[ParisiiCannons]" + message);
    }

    @Override
    public void saveDefaultConfig() {
        saveResource("config.yml", true);
    }

}