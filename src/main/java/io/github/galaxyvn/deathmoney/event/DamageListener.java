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
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener, Configurable {

    private int moneyDropPercent;
    private boolean heartAlertTitle;
    private int heartCheck;
    private int titleFadeIn;
    private int titleStayIn;
    private int titleFadeOut;

    @Override
    public void updateConfig(ConfigurationSection section) {
        moneyDropPercent = section.getInt(ConfigPath.MONEY_DROP_PERCENT);
        heartAlertTitle = section.getBoolean(ConfigPath.HEART_ALERT_TITLE_ENABLED);
        heartCheck = section.getInt(ConfigPath.HEART_CHECK);
        titleStayIn = section.getInt("global.heart.title.fade-in");
        titleStayIn = section.getInt("global.heart.title.stay-in");
        titleStayIn = section.getInt("global.heart.title.fade-out");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player)) return;
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            double money = DeathMoneyHelper.checkMoney(player) * ((double) moneyDropPercent / 100);

            if (player.getHealth() - e.getDamage() <= heartCheck * 2) {
                if (heartAlertTitle)
                    Messager.sendTitle(player,
                            DeathMoneyFiles.getMessages().getString("Heart.alert.title")
                                .replace("{0}", String.valueOf((int) Math.round(money))),
                            DeathMoneyFiles.getMessages().getString("Heart.alert.sub-title")
                                .replace("{0}", String.valueOf((int) Math.round(money))),
                        titleFadeIn,
                        titleStayIn,
                        titleFadeOut
                );
            }
        }
    }
}
