// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccpreetytab;

import pl.best241.ccpreetytab.manager.PreetyTabManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.ccpreetytab.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CcPreetyTab extends JavaPlugin
{
    private static CcPreetyTab plugin;
    
    public void onEnable() {
        CcPreetyTab.plugin = this;
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        PreetyTabManager.listen();
    }
    
    public static CcPreetyTab getPlugin() {
        return CcPreetyTab.plugin;
    }
}
