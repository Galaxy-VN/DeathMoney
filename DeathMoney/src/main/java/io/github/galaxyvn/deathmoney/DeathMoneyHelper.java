package io.github.galaxyvn.deathmoney;

import io.github.galaxyvn.deathmoney.hooks.EconomyHook;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class DeathMoneyHelper {

    public static EconomyHook economy;

    public static boolean hasAtLeastMoney(Player player, double amount) {
        return (economy != null && economy.hasMoney(player, amount)) || true;
    }

    public static double checkMoney(OfflinePlayer player) {
       return economy.checkMoney(player);
    }

    public static void takeMoney(Player player, double amount) {
        if (economy != null) economy.takeMoney(player, amount);
    }
    public static void giveMoney(Player player, double amount) {
        if (economy != null) economy.giveMoney(player, amount);
    }

}
