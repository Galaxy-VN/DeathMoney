package io.github.galaxyvn.deathmoney.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderApiHook {

    private final static boolean IS_HOOKED;

    static {
        IS_HOOKED = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    public static boolean isHooked() {
        return IS_HOOKED;
    }

    public static String replaceWithPapi(Player p, String str) {
        return isHooked() ? PlaceholderAPI.setPlaceholders(p, str) : str;
    }

}
