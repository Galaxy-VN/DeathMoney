package io.github.galaxyvn.deathmoney.event;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.DeathMoneyHelper;
import io.github.galaxyvn.deathmoney.config.ConfigPath;
import io.github.galaxyvn.deathmoney.config.Configurable;
import io.github.galaxyvn.deathmoney.utils.Messager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DeathListener implements Listener, Configurable {

    private int moneyDropPercent;
    private boolean moneyDropMessage;
    private boolean moneyDropTitle;
    private int titleFadeIn;
    private int titleStayIn;
    private int titleFadeOut;
    private boolean moneyDropActionBar;
    private int actionBarDuration;

    @Override
    public void updateConfig(ConfigurationSection section) {
        moneyDropPercent = section.getInt(ConfigPath.MONEY_DROP_PERCENT);
        moneyDropMessage = section.getBoolean(ConfigPath.MONEY_MESSAGE_ENABLED);
        moneyDropTitle = section.getBoolean(ConfigPath.MONEY_TITLE_ENABLED);
        titleStayIn = section.getInt("global.money.title.fade-in");
        titleStayIn = section.getInt("global.money.title.stay-in");
        titleStayIn = section.getInt("global.money.title.fade-out");
        moneyDropActionBar = section.getBoolean(ConfigPath.MONEY_ACTIONBAR_ENABLED);
        actionBarDuration = section.getInt("global.money.actionbar.duration");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        double money = DeathMoneyHelper.checkMoney(player) * ((double) moneyDropPercent / 100);

        DeathMoneyHelper.takeMoney(player, money);
        if (moneyDropMessage) {
            Random r = new Random();
            List<String> messages = DeathMoneyFiles.getMessages().getStringList("Death.messages.content");
            int message = r.nextInt(messages.size());
            String random = messages.get(message);
            Messager.sendTo(player, random
                    .replace("{0}", String.valueOf((int) Math.round(money))));
        }
        if (moneyDropTitle) {
            Messager.sendTitle(player,
                    DeathMoneyFiles.getMessages().getString("Death.title.title")
                            .replace("{0}", String.valueOf((int) Math.round(money))),
                    DeathMoneyFiles.getMessages().getString("Death.title.sub-title")
                            .replace("{0}", String.valueOf((int) Math.round(money))),
                    titleFadeIn,
                    titleStayIn,
                    titleFadeOut
                    );
        }
        if (moneyDropActionBar) {
            Messager.sendActionBar(player, DeathMoneyFiles.getMessages().getString("Death.actionbar.content")
                            .replace("{0}", String.valueOf((int) Math.round(money))),
                    actionBarDuration
                    );
        }
    }
}
