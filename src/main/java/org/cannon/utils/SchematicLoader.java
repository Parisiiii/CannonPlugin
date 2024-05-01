package org.cannon.utils;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SchematicLoader {

    public SchematicLoader(String schematicName, Player player) {
        File file = new File("plugins/WorldEdit/schematics/" + schematicName);
        Vector playerVector = player.getLocation().toVector();
        Location playerLocation = new Location(player.getWorld(), playerVector.getX(), playerVector.getY(), playerVector.getZ());
        BlockVector blockVector = BlockVector.toBlockPoint(playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ());

        WorldData worldData = new BukkitWorld(player.getWorld()).getWorldData();

        Clipboard schem = null;
        try {
            ClipboardFormat format = ClipboardFormat.findByFile(file);
            ClipboardReader reader = format.getReader(new FileInputStream(file));
            schem = reader.read(worldData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(playerLocation.getWorld()), -1);

        if (schem != null) {
            ClipboardHolder toCreate = new ClipboardHolder(schem, worldData);
            toCreate.setTransform(new AffineTransform().rotateY(CardinalDirection.getCardinalDirection(player).getRotation() - (CardinalDirection.getCardinalDirection(player).getRotation() * 2)));

            Operation operation = toCreate
                    .createPaste(session, worldData)
                    .to(blockVector)
                    .ignoreAirBlocks(true)
                    .build();
            try {
                Operations.complete(operation);
                session.flushQueue();
            } catch (WorldEditException e) {
                player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Ocorreu um erro, contate um administrador");
                e.printStackTrace();
            }
            ItemStack itemInHand = player.getItemInHand();
            itemInHand.setAmount(itemInHand.getAmount() - 1);
            player.setItemInHand(itemInHand);
        }
    }
}
