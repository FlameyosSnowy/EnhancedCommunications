package me.flame.communication.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlaceholderHook {
    public static boolean containsPlaceholderAPIPlugin() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
