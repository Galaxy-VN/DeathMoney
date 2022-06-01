package io.github.galaxyvn.deathmoney;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DeathMoneyFiles {

    private static File message_file;
    private static FileConfiguration messages;

    public static void loadFiles() {
        message_file = new File(DeathMoney.getInstance().getDataFolder(), "messages.yml");

        if (!message_file.exists()) {
            DeathMoney.getInstance().saveResource("messages.yml", true);
        }
    }

    public static void loadConfigurations() {
        messages = YamlConfiguration.loadConfiguration(message_file);
    }

    /**
     *
     * @return messages.yml
     */
    public static FileConfiguration getMessages() {
        return messages;
    }
}
