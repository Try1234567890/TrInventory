package me.tr.trInventory.managers.hooks;

import org.bukkit.Bukkit;

public class PlaceholderAPIHook {

    public static boolean isEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

}
