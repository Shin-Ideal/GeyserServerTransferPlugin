package com.github.shin_ideal.geyserservertransferplugin.velocity.Commands;

import com.github.shin_ideal.geyserservertransferplugin.velocity.GeyserServerTransferPluginVelocity;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.Optional;
import java.util.UUID;

public class BeTransferCommandVelocity implements SimpleCommand {

    private final GeyserServerTransferPluginVelocity Instance;

    public BeTransferCommandVelocity() {
        Instance = GeyserServerTransferPluginVelocity.getInstance();
        CommandManager commandManager = Instance.getServer().getCommandManager();
        commandManager.register(commandManager.metaBuilder("betransfer").aliases("btransfer", "beserverredirect", "beserverredirect", "geysertransfer", "gtransfer").build(), this);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (!source.hasPermission("GeyserServerTransferPlugin.command.betransfer")) {
            source.sendMessage(Component.text("You don't have permission.").color(TextColor.color(255, 0, 0)));
            return;
        }

        if (args.length == 0) {
            sendHowToUse(source);
        } else {
            if (args.length > 1) {
                String ip = args[0];
                int port;
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    source.sendMessage(Component.text("Syntax Error").color(TextColor.color(255, 255, 255)));
                    return;
                }
                Player player;
                if (args.length > 2) {
                    if (!source.hasPermission("GeyserServerTransferPlugin.command.betransferforce")) {
                        source.sendMessage(Component.text("Have No Permission").color(TextColor.color(255, 255, 255)));
                        return;
                    }
                    Optional<Player> optionalPlayer = Instance.getServer().getPlayer(args[2]);
                    player = optionalPlayer.get();
                } else {
                    if (!(source instanceof Player)) {
                        source.sendMessage(Component.text("Player Only Command").color(TextColor.color(255, 255, 255)));
                        return;
                    }
                    player = (Player) source;
                }
                if (player == null) {
                    source.sendMessage(Component.text("Player Not Found").color(TextColor.color(255, 255, 255)));
                    return;
                }
                UUID uuid = player.getUniqueId();
                FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(uuid);
                if (floodgatePlayer == null) {
                    source.sendMessage(Component.text("Bedrock User Only Command").color(TextColor.color(255, 255, 255)));
                    return;
                }
                floodgatePlayer.transfer(ip, port);
            } else {
                source.sendMessage(Component.text("Syntax Error").color(TextColor.color(255, 255, 255)));
            }
        }
    }

    private void sendHowToUse(CommandSource source) {
        source.sendMessage(Component.text("/betransfer ip port (username)").color(TextColor.color(255, 255, 255)));
    }
}
