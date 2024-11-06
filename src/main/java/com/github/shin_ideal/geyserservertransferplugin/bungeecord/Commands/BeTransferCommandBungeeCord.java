package com.github.shin_ideal.geyserservertransferplugin.bungeecord.Commands;

import com.github.shin_ideal.geyserservertransferplugin.bungeecord.GeyserServerTransferPluginBungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.UUID;

public class BeTransferCommandBungeeCord extends Command {

    private final GeyserServerTransferPluginBungeeCord Instance;

    public BeTransferCommandBungeeCord() {
        super("betransfer", "GeyserServerTransferPlugin.command.betransfer", "btransfer", "beserverredirect", "beserverredirect", "geysertransfer", "gtransfer");
        Instance = GeyserServerTransferPluginBungeeCord.getInstance();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendHowToUse(sender);
        } else {
            if (args.length > 1) {
                String ip = args[0];
                int port;
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    sender.sendMessage(ChatColor.RED + "Syntax Error");
                    return;
                }
                ProxiedPlayer player;
                if (args.length > 2) {
                    if (!sender.hasPermission("GeyserServerTransferPlugin.command.betransferforce")) {
                        sender.sendMessage(ChatColor.RED + "Have No Permission");
                        return;
                    }
                    player = Instance.getProxy().getPlayer(args[2]);
                } else {
                    if (!(sender instanceof ProxiedPlayer)) {
                        sender.sendMessage(ChatColor.RED + "Player Only Command");
                        return;
                    }
                    player = (ProxiedPlayer) sender;
                }
                if (player == null) {
                    sender.sendMessage("Player Not Found");
                    return;
                }
                UUID uuid = player.getUniqueId();
                FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(uuid);
                if (floodgatePlayer == null) {
                    sender.sendMessage(ChatColor.RED + "Bedrock User Only Command");
                    return;
                }
                floodgatePlayer.transfer(ip, port);
            } else {
                sender.sendMessage(ChatColor.RED + "Syntax Error");
            }
        }
    }

    private void sendHowToUse(CommandSender sender) {
        sender.sendMessage("/betransfer ip port (username)");
    }
}
