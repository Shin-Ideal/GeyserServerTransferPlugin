package com.github.shin_ideal.geyserservertransferplugin.bungeecord;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginMessageListenerBungeeCord implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equals("betransfer:transfer")) {
            return;
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
        DataInputStream in = new DataInputStream(stream);
        try {
            String subChannel = in.readUTF();
            switch (subChannel) {
                case "betransfer":
                    String playerUUID = in.readUTF();
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(playerUUID));
                    String args = in.readUTF();
                    ProxyServer.getInstance().getPluginManager().dispatchCommand(player, "betransfer " + args);
                    ProxyServer.getInstance().getLogger().info(player.getName() + " send /betransfer " + args);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
