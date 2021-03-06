package de.themoep.resourcepacksplugin.bungee.listeners;

import de.themoep.resourcepacksplugin.bungee.BungeeResourcepacks;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Phoenix616 on 14.05.2015.
 */
public class DisconnectListener implements Listener {

    private BungeeResourcepacks plugin;

    public DisconnectListener(BungeeResourcepacks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        if(plugin.isEnabled()) {
            plugin.getUserManager().onDisconnect(event.getPlayer().getUniqueId());
            plugin.setAuthenticated(event.getPlayer().getUniqueId(), false);
        }
    }
}
