package com.github.shin_ideal.geyserservertransferplugin.bungeecord;

import com.github.shin_ideal.geyserservertransferplugin.bungeecord.Commands.BeTransferCommandBungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class GeyserServerTransferPluginBungeeCord extends Plugin {

    private static GeyserServerTransferPluginBungeeCord Instance;

    public static GeyserServerTransferPluginBungeeCord getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        Instance = this;
        registerCommandExecutors();
        registerPluginChannels();
        getLogger().info("enable");
    }

    @Override
    public void onDisable() {
        getLogger().info("disable");
    }

    private void registerCommandExecutors() {
        getProxy().getPluginManager().registerCommand(this, new BeTransferCommandBungeeCord());
    }

    private void registerPluginChannels() {
        getProxy().registerChannel("betransfer:transfer");
        getProxy().getPluginManager().registerListener(this, new PluginMessageListenerBungeeCord());
    }
}
