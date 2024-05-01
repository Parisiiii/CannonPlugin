package org.cannon.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cannon.manager.CannonManager;
import org.cannon.model.Cannon;

import static org.bukkit.Bukkit.getPlayer;

public class CannonCommand implements CommandExecutor {
    private final CannonManager cannonManager;

    public CannonCommand(CannonManager cannonManager) {
        this.cannonManager = cannonManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.DARK_RED + "Uso do comando errado! Tente /cannon <cannonId> <player>");
            return true;
        }

        final String rawPlayer = args[1];
        final Player targetPlayer = getPlayer(rawPlayer);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Player não encontrado!");
            return true;
        }

        final String configYmlCannonString = args[0];
        final Cannon cannon = cannonManager.getCannonMap().get(configYmlCannonString);
        if (cannon == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Este canhão não existe!");
        }
        sender.sendMessage(ChatColor.AQUA + "Canhão enviado para " + targetPlayer.getName());
        targetPlayer.getInventory().addItem(cannon.getItemStack());

        return false;
    }
}
