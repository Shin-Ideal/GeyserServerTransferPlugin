package com.github.shin_ideal.geyserservertransferplugin.spigot;

import com.github.shin_ideal.geyserservertransferplugin.velocity.GeyserServerTransferPluginVelocity;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginMessageListenerVelocity {

    @Subscribe
    public void onPluginMessageFromBackend(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(MinecraftChannelIdentifier.from("betransfer:transfer"))) {
            return;
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        try {
            String subChannel = in.readUTF();
            switch (subChannel) {
                case "betransfer":
                    String playerUUID = in.readUTF();
                    Player player = GeyserServerTransferPluginVelocity.getInstance().getServer().getPlayer(UUID.fromString(playerUUID)).get();
                    String args = in.readUTF();
                    GeyserServerTransferPluginVelocity.getInstance().getServer().getCommandManager().executeAsync(player, "betransfer " + args);
                    GeyserServerTransferPluginVelocity.getInstance().getLogger().info(player.getUsername() + " send /betransfer " + args);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
