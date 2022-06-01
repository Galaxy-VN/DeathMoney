package io.github.galaxyvn.deathmoney.placeholders;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyHelper;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public class DeathMoneyExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "deathmoney";
    }

    @Override
    public String getAuthor() {
        return "GalaxyVN";
    }

    @Override
    public String getVersion() {
        return DeathMoney.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {


        // money-lost
        if (identifier.equalsIgnoreCase("lost")) {
            return String.valueOf((int) Math.round(DeathMoneyHelper.checkMoney(player) * ((double) DeathMoney.getInstance().getConfig().getInt("global.money.drop-percent") / 100)));
        }

        return null;
    }
}
