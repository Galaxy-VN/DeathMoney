package io.github.galaxyvn.deathmoney.commands;

import io.github.galaxyvn.deathmoney.utils.Hex;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

public abstract class DeathCommand implements CommandExecutor, TabCompleter {

    private String name;
    private String label;
    private CommandSender sender;
    private boolean playerOnly;

    public DeathCommand(String name) {
        this.name = name;
        if (getClass().isAnnotationPresent(PlayerOnly.class)) {
            playerOnly = true;
        }
    }

    public void msg(CommandSender sender, String msg) {
        sender.sendMessage(Hex.colorify(msg));
    }

    public void msg(String msg) {
        sender.sendMessage(Hex.colorify(msg));
    }

    public void msg(Object msg) {
        sender.sendMessage(Hex.colorify(msg.toString()));
    }

    @Override
    public final List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cm, @Nonnull String label, @Nonnull String[] args) {
        return tabComplete(args);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        this.sender = sender;
        this.label = label;
        if (playerOnly && wasPlayer())
            execute(args);
        else if (!playerOnly)
            execute(args);
        return false;
    }

    public String getName() {
        return name;
    }

    public String getLabelUsed() {
        return label;
    }

    public boolean wasPlayer() {
        return sender instanceof Player;
    }

    public CommandSender getSender() {
        return sender;
    }

    public abstract void execute(String[] args);

    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlayerOnly {

    }
}
