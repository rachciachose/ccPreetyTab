// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccpreetytab.manager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.Bukkit;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.events.PacketAdapter;
import pl.best241.ccpreetytab.CcPreetyTab;
import pl.best241.ccscoreboards.PacketDataManager;
import org.bukkit.ChatColor;
import com.comphenix.protocol.events.PacketContainer;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.comphenix.protocol.PacketType;
import org.bukkit.entity.Player;
import java.util.HashMap;
import com.comphenix.protocol.ProtocolManager;

public class PreetyTabManager
{
    private static final ProtocolManager protocolManager;
    private static HashMap<Integer, String> layer;
    private static final int maxTabSize = 60;
    
    public static void loadFirstLayer(final Player player) {
        if (PreetyTabManager.layer == null) {
            loadLayer();
        }
        final int version = PreetyTabManager.protocolManager.getProtocolVersion(player);
        if (version == 5 || version == 4) {
            for (final String playerName : PreetyTabManager.layer.values()) {
                try {
                    final PacketContainer tabPacket = PreetyTabManager.protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
                    tabPacket.getStrings().write(0, (Object)playerName);
                    PreetyTabManager.protocolManager.sendServerPacket(player, tabPacket);
                }
                catch (InvocationTargetException ex) {
                    Logger.getLogger(PreetyTabManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private static void loadLayer() {
        PreetyTabManager.layer = new HashMap<Integer, String>();
        for (int i = 0; i < 60; ++i) {
            final String hexField = Integer.toHexString(i);
            PreetyTabManager.layer.put(i, ChatColor.translateAlternateColorCodes('&', getField(hexField)));
        }
    }
    
    private static String getField(final String hexValue) {
        final int hexlenght = hexValue.length();
        final char[] chars = new char[hexlenght];
        hexValue.getChars(0, hexlenght, chars, 0);
        String field = "";
        for (final char singleChar : chars) {
            field = field + "&" + singleChar;
        }
        return field;
    }
    
    public static void setTabField(String string, final int x, final int y, final Player player) {
        final int hex = x + 3 * y;
        final String layerName = PreetyTabManager.layer.get(hex);
        if (string.length() > 30) {
            string = string.substring(0, 30);
        }
        if (string.length() > 16) {
            final String prefix = string.substring(0, 16);
            String suffix = string.substring(16, string.length());
            final String lastColor = ChatColor.getLastColors(prefix);
            suffix = lastColor + suffix;
            if (suffix == null) {
                suffix = "";
            }
            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }
            PacketDataManager.sendTeamPacketUpdate(player, layerName, "", prefix, suffix, (byte)0);
        }
        else {
            PacketDataManager.sendTeamPacketUpdate(player, layerName, "", string, "", (byte)0);
        }
    }
    
    public static void listen() {
        PreetyTabManager.protocolManager.addPacketListener((PacketListener)new PacketAdapter(CcPreetyTab.getPlugin(), new PacketType[] { PacketType.Play.Server.PLAYER_INFO }) {
            public void onPacketSending(final PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {
                    final int version = PreetyTabManager.protocolManager.getProtocolVersion(event.getPlayer());
                    if (version == 5 || version == 4) {
                        final PacketContainer packet = event.getPacket();
                        final String tab = (String)packet.getStrings().read(0);
                        for (final Player player : Bukkit.getOnlinePlayers()) {
                            if (tab.equals(player.getName()) || tab.equals(player.getDisplayName())) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        });
    }
    
    static {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }
}
