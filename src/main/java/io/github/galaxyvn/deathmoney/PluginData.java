package io.github.galaxyvn.deathmoney;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class PluginData {
    public static final String NAME = "DeathMoney";
    public static final int RESOURCE_ID = 34850;

    public static final String PREFIX = Colors.PRIMARY + "[" + NAME + "]" + Colors.MESSAGE + " ";
    public static final String PLUGIN_PAGE = "https://minecraftvn.net/deathmoney.t34850/";
    public static final String ISSUES_PAGE = "";
    public static final String WIKI_PAGE = "";
    public static final String DISCORD = "";

    public static class Colors {

        public static final ChatColor PRIMARY = ChatColor.of(new Color(171, 255, 45));
        public static final ChatColor SECONDARY = ChatColor.of(new Color(237, 102, 12));
        public static final ChatColor TERTIARY = ChatColor.of(new Color(197, 248, 142));
        public static final ChatColor ACTION = ChatColor.of(new Color(71, 234, 220));
        public static final ChatColor MESSAGE = ChatColor.of(new Color(207, 215, 217));
        public static final ChatColor ERROR = ChatColor.RED;

        private ChatColor lastUsed = ChatColor.WHITE;
        private StringBuilder text = new StringBuilder();

        public Colors() {
        }

        public Colors text(String t) {
            text.append(lastUsed);
            text.append(t);
            return this;
        }

        public Colors primary() {
            lastUsed = PRIMARY;
            return this;
        }

        public Colors primary(String t) {
            this.text.append(PRIMARY).append(t).append(lastUsed);
            return this;
        }

        public Colors secondary() {
            lastUsed = SECONDARY;
            return this;
        }

        public Colors secondary(String t) {
            this.text.append(SECONDARY).append(t).append(lastUsed);
            return this;
        }

        public Colors message() {
            lastUsed = MESSAGE;
            return this;
        }

        public Colors message(String t) {
            this.text.append(MESSAGE).append(t).append(lastUsed);
            return this;
        }

        public Colors error() {
            lastUsed = ERROR;
            return this;
        }

        public Colors error(String t) {
            this.text.append(ERROR).append(t).append(lastUsed);
            return this;
        }

        @Override
        public String toString() {
            return text.toString();
        }
    }
}
