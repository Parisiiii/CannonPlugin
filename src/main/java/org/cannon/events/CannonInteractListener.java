package org.cannon.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.cannon.manager.CannonManager;
import org.cannon.model.Cannon;
import org.cannon.utils.NBTGetter;
import org.cannon.utils.SchematicLoader;

public class CannonInteractListener implements Listener {
    private final CannonManager cannonManager;
    private final FileConfiguration fileConfiguration;

    public CannonInteractListener(CannonManager cannonManager, FileConfiguration fileConfiguration) {
        this.cannonManager = cannonManager;
        this.fileConfiguration = fileConfiguration;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        String nbtTag = NBTGetter.getTagValue("ParisiCannon", item);
        if (item == null) return;

        Cannon cannon = cannonManager.getCannonMap().get(nbtTag);

        if (cannon == null) return;
        if (!item.isSimilar(cannon.getItemStack())) return;

        // Carregando o schema
        new SchematicLoader(cannon.getSchematicName(), player);
        player.sendMessage(String.format(cannon.getSuccessMessage()));

        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            event.setCancelled(true);
        }
    }
}
