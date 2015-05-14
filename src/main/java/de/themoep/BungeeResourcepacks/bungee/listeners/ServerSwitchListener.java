package de.themoep.BungeeResourcepacks.bungee.listeners;

import de.themoep.BungeeResourcepacks.bungee.BungeeResourcepacks;
import de.themoep.BungeeResourcepacks.core.ResourcePack;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Phoenix616 on 24.03.2015.
 */
public class ServerSwitchListener implements Listener {
    
    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        BungeeResourcepacks plugin = BungeeResourcepacks.getInstance();
        if(!plugin.enabled) return;

        final UUID playerid = event.getPlayer().getUniqueId();
        plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                BungeeResourcepacks plugin = BungeeResourcepacks.getInstance();
                ProxiedPlayer player = plugin.getProxy().getPlayer(playerid);
                if(player != null) {
                    ResourcePack pack = null;
                    Server server = player.getServer();
                    if(server != null) {
                        pack = plugin.getPackManager().getServerPack(server.getInfo().getName());
                    }
                    if(pack == null) {
                        pack = plugin.getPackManager().getGlobalPack();
                    }
                    if(pack == null && plugin.getPackManager().getUserPack(playerid) != null) {
                        pack = plugin.getPackManager().getEmptyPack();
                    }
                    if(pack != null) {
                        plugin.setPack(player, pack);
                    }
                }
            }
        }, 300L, TimeUnit.MILLISECONDS);
    }
}
