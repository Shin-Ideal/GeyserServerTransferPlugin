package com.github.shin_ideal.geyserservertransferplugin.CommandExecutors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.UUID;

public class BeTransferCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHowToUse(sender);
            return true;
        } else {
            if (args.length > 1) {
                String ip = args[0];
                int port;
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    sender.sendMessage(ChatColor.RED + "Syntax Error");
                    return false;
                }
                Player player;
                if (args.length > 2) {
                    if (!sender.hasPermission("GeyserServerTransferPlugin.command.betransferforce")) {
                        sender.sendMessage(ChatColor.RED + "Have No Permission");
                        return false;
                    }
                    player = Bukkit.getPlayer(args[2]);
                } else {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "Player Only Command");
                        return false;
                    }
                    player = (Player) sender;
                }
                if (player == null) {
                    sender.sendMessage("Player Not Found");
                    return false;
                }
                UUID uuid = player.getUniqueId();
                FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(uuid);
                if (floodgatePlayer == null) {
                    sender.sendMessage(ChatColor.RED + "Bedrock User Only Command");
                    return false;
                }
                return floodgatePlayer.transfer(ip, port);
            } else {
                sender.sendMessage(ChatColor.RED + "Syntax Error");
                return false;
            }
        }
    }

    private void sendHowToUse(CommandSender sender) {
        sender.sendMessage("/betransfer ip port (username)");
    }
}
