package io.github.galaxyvn.deathmoney.menus;

import io.github.galaxyvn.deathmoney.DeathMoney;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.*;

public final class MenuManager implements Listener {

    private DeathMoney plugin;
    private Map<String, GlobalMenu> menus = new HashMap<>();

    public MenuManager(DeathMoney plugin) {
        this.plugin = plugin;
    }

    public void register(GlobalMenu... menu) {
        for (GlobalMenu m : menu) {
            menus.put(m.getID(), m);
        }
    }

    public Collection<GlobalMenu> getMenus() {
        return menus.values();
    }

    public void forceCloseAll() {
        for (GlobalMenu m : menus.values()) {
            m.forceClose();
        }
    }

    public void rebuildAllMenus() {
        for (GlobalMenu m : menus.values()) {
            m.rebuild();
        }
    }

    public void remove(String id) {
        menus.remove(id);
    }

    /**
     * Returns a menu from an id. If the menu has not been registered then
     * null will be returned.
     *
     * @param id id of menu to get.
     * @return the menu with that id or null if none exist.
     */
    public GlobalMenu getMenu(Menus id) {
        return menus.get(id.getId());
    }

    /**
     * Returns a menu from an id. If the menu has not been registered then
     * null will be returned.
     *
     * @param id id of menu to get.
     * @return the menu with that id or null if none exist.
     */
    public GlobalMenu getMenu(String id) {
        return menus.get(id);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (inv == null) return;

        // Find a menu that matches then run the on click event if there is a match found.
        for (GlobalMenu menu : menus.values()) {
            if (menu.isInventorySimilar(inv)) {
                menu.processClick(event.getSlot(), (Player) event.getWhoClicked(), event.getClick());
                event.setCancelled(true);
                break;
            }
        }
    }
}
