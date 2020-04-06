// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccpreetytab.listeners;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import pl.best241.ccpreetytab.manager.PreetyTabManager;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        PreetyTabManager.loadFirstLayer(player);
    }
    
    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
    }
}
