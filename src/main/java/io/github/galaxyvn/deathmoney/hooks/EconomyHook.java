package io.github.galaxyvn.deathmoney.hooks;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class EconomyHook {

    public abstract void giveMoney(Player player, double amount);
    public abstract void takeMoney(Player player, double amount);
    public abstract double checkMoney(OfflinePlayer player);
    public abstract boolean hasMoney(Player player, double amount);
}
