package io.github.galaxyvn.deathmoney.menus;

public enum Menus {

    SETTINGS("settings_main"),
    SETTINGS_MESSAGES("settings_messages"),
    MESSAGES("messages_menu");

    private final String id;

    Menus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
