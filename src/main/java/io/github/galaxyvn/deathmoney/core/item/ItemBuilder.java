package io.github.galaxyvn.deathmoney.core.item;

import io.github.galaxyvn.deathmoney.PluginData;
import io.github.galaxyvn.deathmoney.utils.Hex;
import io.github.galaxyvn.deathmoney.utils.StringUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack stack;
    private Material type;
    private String name;
    private List<String> lore = new ArrayList<>();
    private PotionType potionType;
    private boolean glowing;
    private int customModel = -1;

    public ItemBuilder(Material type) {
        this.type = type;
    }

    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
        this.type = stack.getType();
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            this.name = meta.getDisplayName();
            if (meta.hasLore())
                this.lore.addAll(meta.getLore());
        }
    }

    public ItemBuilder(SkullIndex skullSkin) {
        this.stack = skullSkin.asItem();
    }

    public ItemBuilder setName(String name) {
        this.name = Hex.colorify(name);
        return this;
    }

    public ItemBuilder setName(String name, ChatColor color) {
        this.name = color + name;
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        for (String s : lore) {
            this.lore.add(Hex.colorify(s));
        }
        return this;
    }

    public ItemBuilder addAction(String action) {
        return addDescription(action, PluginData.Colors.ACTION, false);
    }

    /**
     * Adds a description to the item. Descriptions are padded on the top and bottom and
     * will auto-wrap words when over a threshold length.
     *
     * @param desc description text to append to the items lore.
     * @return an instance of the item builder.
     */
    public ItemBuilder addDescription(String desc) {
        return addDescription(desc, PluginData.Colors.MESSAGE);
    }


    /**
     * Adds a description to the item. Descriptions are padded on the top and bottom and
     * will auto-wrap words when over a threshold length.
     *
     * @param desc  description text to append to the items lore.
     * @param color sets the colour of this description section.
     * @return an instance of the item builder.
     */
    public ItemBuilder addDescription(String desc, ChatColor color) {
        return addDescription(desc, color, true);
    }


    /**
     * Adds a description to the item. Descriptions are padded on the top and bottom and
     * will auto-wrap words when over a threshold length.
     *
     * @param desc  description text to append to the items lore.
     * @param color sets the colour of this description section.
     * @return an instance of the item builder.
     */
    public ItemBuilder addDescription(String desc, ChatColor color, boolean paddingBottom) {
        StringBuilder line = new StringBuilder();
        addLore("");
        boolean complete = false;
        String[] words = desc.split(" ");
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            // Subtract the color codes from lines length
            int trulLen = line.length() - StringUtil.getColorCodeCount(line.toString()) * 2;
            if (trulLen >= 25) {
                addLore(color + line.toString());
                line = new StringBuilder();
                if (i >= words.length - 1)
                    complete = true;
            } else {
                complete = false;
            }
            line.append(s);
            line.append(" ");
        }
        if (!complete)
            addLore(color.toString() + line.toString());
        if (paddingBottom) addLore("");
        return this;
    }

    public ItemBuilder addSwitch(String prefix, boolean toggle) {
        addLore(prefix + (toggle ? "&a Enabled" : "&c Disabled"),
                "",
                PluginData.Colors.ACTION + "Click to " + (toggle ? "disable" : "enable"));
        return this;
    }

    public ItemBuilder addSwitchView(String prefix, boolean toggle) {
        addLore(prefix + (toggle ? "&a Enabled" : "&c Disabled"));
        return this;
    }

    public ItemBuilder addSwitch(String prefix, String on, String off, boolean toggle) {
        addLore(prefix + (toggle ? on : off), PluginData.Colors.ACTION + "Click to " + (toggle ? off : on));
        return this;
    }

    public ItemBuilder setPotionType(PotionType type) {
        this.potionType = type;
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    /**
     * Sets the custom model id used for a resource pack.
     *
     * @param id id of the custom model.
     * @return an instance of this item builder.
     */
    public ItemBuilder setCustomModelData(int id) {
        this.customModel = id;
        return this;
    }

    public ItemStack build() {
        ItemStack item = stack == null ? new ItemStack(type) : stack;

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if (customModel > 0) {
            meta.setCustomModelData(customModel);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        item.setItemMeta(meta);
        if (meta instanceof PotionMeta && potionType != null) {
            PotionMeta potionMeta = (PotionMeta) meta;
            potionMeta.setBasePotionData(new PotionData(potionType));
            item.setItemMeta(potionMeta);
        }
        return item;
    }
}
