package com.github.shin_ideal.geyserservertransferplugin.spigot;

import com.github.shin_ideal.geyserservertransferplugin.spigot.CommandExecutors.BeTransferCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeyserServerTransferPlugin extends JavaPlugin {

    private static GeyserServerTransferPlugin Instance;
    private boolean floodgateMode;

    public static GeyserServerTransferPlugin getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        floodgateMode = getServer().getPluginManager().getPlugin("floodgate") != null;
        registerCommandExecutors();
        registerPluginChannels();
        getLogger().info("enable");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("disable");
    }

    private void registerCommandExecutors() {
        getCommand("betransfer").setExecutor(new BeTransferCommandExecutor());
    }

    private void registerPluginChannels() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "betransfer:transfer");
    }

    public boolean isFloodgateMode() {
        return floodgateMode;
    }
}
