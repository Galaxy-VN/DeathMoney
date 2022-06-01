package io.github.galaxyvn.deathmoney.menus;

import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.PluginData;
import io.github.galaxyvn.deathmoney.core.item.ItemBuilder;
import io.github.galaxyvn.deathmoney.utils.Hex;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import io.github.galaxyvn.deathmoney.libary.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Button {

    public enum Common {
        EDIT("edit"),
        CHANGE("change"),
        SELECTED("selected"),
        SELECT("select"),
        ENABLE("enable"),
        DISABLE("disable"),
        RESET("reset"),
        RELOAD("reload"),

        LOW("low"),
        MEDIUM("medium"),
        HIGH("high"),
        EXTREME("extreme"),
        CUSTOM("custom");

        String index;
        ChatColor color;

        Common(String index) {
            this.index = "menu.common." + index;
            this.color = PluginData.Colors.ACTION;
        }

//        public String text(LanguageEngine engine) {
//            return engine.getText(this.index);
//        }
        public String text() {
            return DeathMoneyFiles.getMessages().getString(this.index);
        }
    }

    private GlobalMenu menu;
    private final UpdateIcon UPDATER;
    private final int SLOT;
    ItemStack stack;
    private UpdateToggle updateToggle;
    private ClickEvent clickEvent;
    private boolean toggleDisplay = false, enabled;
    private String toggleName = "";

    /**
     * Creates a new button.
     *
     * @param slot slot this button is to be displayed in.
     * @param icon icon used to display for this button.
     */
    public Button(int slot, ItemStack icon) {
        this(slot, () -> icon);
    }

    /**
     * Creates a new button.
     *
     * @param slot slot this button is to be displayed in.
     * @param updateIcon method used to get the buttons icon.
     */
    public Button(int slot, UpdateIcon updateIcon) {
        this.SLOT = slot;
        this.UPDATER = updateIcon;
        stack = updateIcon.run();
    }

    Button setMenu(GlobalMenu menu) {
        this.menu = menu;
        return this;
    }

    /**
     * Sets the click method.
     *
     * @param event method used to handle the click event.
     */
    public Button onClick(ClickEvent event) {
        clickEvent = event;
        return this;
    }

    public Button setToggle(String name, UpdateToggle toggle) {
        toggleDisplay = true;
        updateToggle = toggle;
        this.toggleName = Hex.colorify(name);
        return this;
    }

    /**
     * Attempt to click this button. The clicked inventory and slot
     * must be the same as what was specified when creating the button
     * for the click to be actioned.
     *
     * @param player who clicked this button.
     * @param menu   inventory that was clicked in.
     * @param slot   slot that was clicked.
     */
    final void click(Player player, GlobalMenu menu, int slot) {
        if (this.menu == menu && slot == this.SLOT && clickEvent != null) {
            clickEvent.click(player);
            update();
        }
    }

    /**
     * Updates the icon for the button by running its update method.
     */
    public void update() {
        if (menu == null) return;
        stack = UPDATER.run();
        Inventory inv = menu.getInventory();
        if (inv == null) {
            menu.rebuild();
            inv = menu.getInventory();
            if (inv == null) return; // Critical error
        }
        inv.setItem(SLOT, stack);
        if (toggleDisplay) {
            if (updateToggle != null)
                enabled = updateToggle.toggle();
            inv.setItem(SLOT + 9, new ItemBuilder(enabled ? XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial()
                    : XMaterial.GRAY_STAINED_GLASS_PANE.parseMaterial())
                    .setName((enabled ? "&a" : "&8") + toggleName).build());
        }
    }

    /**
     * @return the slot the button is set in the inventory.
     */
    public final int getSlot() {
        return SLOT;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Button)) return false;
        Button button = (Button) o;
        return SLOT == button.SLOT;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(SLOT);
    }

    /**
     * Used for creating an updating icon for a button.
     */
    public interface UpdateIcon {
        ItemStack run();
    }

    /**
     * Used for setting the toggle state of a button.
     */
    public interface UpdateToggle {
        boolean toggle();
    }

    /**
     * Called by the button when it is clicked on.
     */
    public interface ClickEvent {
        void click(Player player);
    }
}
