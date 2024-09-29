package com.github.shin_ideal.geyserservertransferplugin;

import com.github.shin_ideal.geyserservertransferplugin.CommandExecutors.BeTransferCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeyserServerTransferPlugin extends JavaPlugin {

    private static GeyserServerTransferPlugin Instance;

    public static GeyserServerTransferPlugin getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        registerCommandExecutors();
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
}
