package io.github.galaxyvn.deathmoney.config;

public class ConfigPath {

    public static final String GENERAL = "general";
    public static final String UPDATE_CHECK = path(GENERAL, "check-for-updates");
    public static final String UPDATE_NOTIFY = path(GENERAL, "notify-update-join");

    public static final String GLOBAL = "global";
    public static final String MONEY = path(GLOBAL, "money");
    public static final String MONEY_DROP_PERCENT = path(MONEY, "drop-percent");
    public static final String MONEY_MESSAGE_ENABLED = path(MONEY, "message-enabled");
    public static final String MONEY_TITLE = path(MONEY, "title");
    public static final String MONEY_TITLE_ENABLED = path(MONEY_TITLE, "enabled");

    public static final String MONEY_ACTIONBAR = path(MONEY, "actionbar");
    public static final String MONEY_ACTIONBAR_ENABLED = path(MONEY_ACTIONBAR, "enabled");

    public static final String HEART = path(GLOBAL, "heart");
    public static final String HEART_CHECK = path(HEART, "heart-check");
    public static final String HEART_ALERT_TITLE = path(HEART_CHECK, "title");
    public static final String HEART_ALERT_TITLE_ENABLED = path(HEART_CHECK, "enabled");

    private static String path(String from, String... to) {
        StringBuilder builder = new StringBuilder(from);
        for (String s : to) {
            if (builder.length() > 0) builder.append(".");
            builder.append(s);
        }
        return builder.toString();
    }
}
