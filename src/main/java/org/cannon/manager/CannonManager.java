package org.cannon.manager;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.cannon.ParisiiCannon;
import org.cannon.model.Cannon;
import org.cannon.utils.ItemComposer;

import java.util.HashMap;
import java.util.Map;

public class CannonManager {
    private final ParisiiCannon plugin;

    @Setter
    private FileConfiguration fileConfiguration;

    @Getter
    private final Map<String, Cannon> cannonMap = new HashMap<>();

    public CannonManager(ParisiiCannon plugin, FileConfiguration fileConfiguration) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;
    }

    public void loadCannons() {
        ConfigurationSection configurationSection = fileConfiguration.getConfigurationSection("cannons");
        for (String configYmlKey : configurationSection.getKeys(false)) {
            cannonMap.put(configYmlKey, new Cannon(
                    configYmlKey,
                    fileConfiguration.getString("cannons." + configYmlKey + ".settings.successMessage"),
                    fileConfiguration.getString("cannons." + configYmlKey + ".settings.schematic"),
                    new ItemComposer(fileConfiguration.getString("cannons." + configYmlKey + ".item.texture"))
                            .setName(fileConfiguration.getString("cannons." + configYmlKey + ".item.name"))
                            .setLore(fileConfiguration.getString("cannons." + configYmlKey + ".item.lore"))
                            .setNBTTag("ParisiCannon", configYmlKey)
                            .toItemStack()
            ));
        }
        plugin.debug("§3Foram carregados §a" + getCannonMap().size() + " §3canhões pelo arquivo de configuração.");
//        plugin.debug(ChatColor.DARK_RED + "Config do canhão" + fileConfiguration.getString("cannons." + configYmlKey + "item.lore"))
    }
}
