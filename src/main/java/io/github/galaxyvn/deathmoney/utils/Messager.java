package io.github.galaxyvn.deathmoney.utils;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.hooks.PlaceholderApiHook;
import io.github.galaxyvn.deathmoney.libary.xseries.ActionBar;
import io.github.galaxyvn.deathmoney.libary.xseries.Titles;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Messager {

    private static final String JSON_PREFIX = "[JSON]";

    public static void sendTo(CommandSender target, String message) {
        if (message != null) {
            message = color(target instanceof Player ? PlaceholderApiHook.replaceWithPapi((Player) target, message) : message);
            BaseComponent[] msg = message.startsWith(JSON_PREFIX) ? ComponentSerializer.parse(message.substring(6)) : TextComponent.fromLegacyText(message);

            if (target instanceof Player) {
                ((Player) target).spigot().sendMessage(msg);
            } else {
                target.sendMessage(TextComponent.toLegacyText(msg));
            }
        }
    }

    public static void sendTitle(CommandSender target, String title, String subtitle, int fadein, int stay, int fadeout) {
        if (title != null) {
            title = color(target instanceof Player ? PlaceholderApiHook.replaceWithPapi((Player) target, title) : title);
            subtitle = color(target instanceof Player ? PlaceholderApiHook.replaceWithPapi((Player) target, subtitle) : subtitle);
        }
        if (target instanceof Player) Titles.sendTitle((Player) target,
                fadein * 20,
                stay * 20,
                fadeout * 20,
                title,
                subtitle
        );
    }

    public static void sendActionBar(CommandSender target, String content, long duration) {
        if (content != null) {
            content = color(target instanceof Player ? PlaceholderApiHook.replaceWithPapi((Player) target, content) : content);
        }
        if (target instanceof Player) ActionBar.sendActionBar(DeathMoney.getInstance(), (Player) target, content, duration * 50L);
    }

    public static void consoleExecute(Player p, List<String> commands) {
        commands.forEach(command -> consoleExecute(p, command));
    }

    private static void consoleExecute(Player p, String command) {
        command = PlaceholderApiHook.replaceWithPapi(p, command.replace("{player}", p.getName()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), color(command));
    }

    public static void logString(String path) {
        sendString(Bukkit.getConsoleSender(), path);
    }

    public static void sendString(CommandSender target, String path) {
        String message = DeathMoneyFiles.getMessages().getString(path, null);
        sendTo(target, message);
    }

    public static void sendStrings(CommandSender target, String path) {
        DeathMoneyFiles.getMessages().getStringList(path).forEach(str -> sendTo(target, str));
    }

    private static String color(String str) {
        if (str == null) {
            return null;
        }
        return Hex.colorify(str);
    }
}
