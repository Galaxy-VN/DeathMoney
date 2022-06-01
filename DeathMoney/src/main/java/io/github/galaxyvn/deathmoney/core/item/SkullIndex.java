package io.github.galaxyvn.deathmoney.core.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.github.galaxyvn.deathmoney.libary.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public enum SkullIndex {

    CLOSE("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025"),
    NEXT("19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf"),
    PREVIOUS("bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9"),
    DEATHMONEY("f14efce4431e2afc63640b50f77fce1df8617557e4dbd6a61cd902922cd89d06"),
    MESSAGE("8ae7bf4522b03dfcc866513363eaa9046fddfd4aa6f1f0889f03c1e6216e0ea0"),
    TITLE("20648b624043d0f26e43dbc030e817fa2e8d9b385e4c6294572baf711472769a"),
    ACTIONBAR("eaa6285ab90e6ba98cfdff42f7e67537c844f33bcccbd81aa6d6c06cb281a797");

    private String id;
    private GameProfile profile;
    private ItemStack stack;

    SkullIndex() {
        this("");
    }

    SkullIndex(String id) {
        this.id = id;
        this.stack = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());

        SkullMeta meta = (SkullMeta) this.stack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), this.name());
        profile.getProperties().clear();
        PropertyMap map = profile.getProperties();
        byte[] encoded = Base64.encodeBase64(String.format("{\"textures\":{\"SKIN\":" +
                "{\"url\":\"http://textures.minecraft.net/texture/%s\"}}}", this.id).getBytes());
        map.put("textures", new Property("textures", new String(encoded)));

        this.profile = profile;

        try {
            assert meta != null;
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.stack.setItemMeta(meta);
    }

    public GameProfile getProfile() {
        return profile;
    }

    public String getId() {
        return id;
    }

    public ItemStack asItem() {
        return stack;
    }
}
