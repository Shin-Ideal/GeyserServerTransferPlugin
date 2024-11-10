package com.github.shin_ideal.geyserservertransferplugin.velocity;

import com.github.shin_ideal.geyserservertransferplugin.spigot.PluginMessageListenerVelocity;
import com.github.shin_ideal.geyserservertransferplugin.velocity.Commands.BeTransferCommandVelocity;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

@Plugin(
        id = "geyserservertransferplugin",
        name = "GeyserServerTransferPlugin",
        version = "2.1.0"
)
public class GeyserServerTransferPluginVelocity {

    private static GeyserServerTransferPluginVelocity Instance;

    private final ProxyServer server;

    @Inject
    private Logger logger;

    public static GeyserServerTransferPluginVelocity getInstance() {
        return Instance;
    }

    @Inject
    public GeyserServerTransferPluginVelocity(ProxyServer server, Logger logger) {
        Instance = this;
        this.server = server;
        this.logger = logger;
        logger.info("Enable");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        registerCommandExecutors();
        registerPluginChannels();
    }

    private void registerCommandExecutors() {
        new BeTransferCommandVelocity();
    }

    private void registerPluginChannels() {
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.from("betransfer:transfer"));
        server.getEventManager().register(this, new PluginMessageListenerVelocity());
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }
}
