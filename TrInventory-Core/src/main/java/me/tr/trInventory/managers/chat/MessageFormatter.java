package me.tr.trInventory.managers.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.tr.trInventory.managers.hooks.PlaceholderAPIHook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MessageFormatter {

    public static net.minecraft.network.chat.Component toNMS(Component component) {
        try {
            Class<?> clazz = Class.forName("io.papermc.paper.adventure.PaperAdventure");
            Method method = clazz.getDeclaredMethod("asVanilla", Component.class);
            return (net.minecraft.network.chat.Component) method.invoke(null, component);
        } catch (Exception e) {
            throw new RuntimeException("Cannot convert Adventure Component to NMS Component", e);
        }
    }


    public Component format(String msg, Player player, Player target) {
        if (player != null) msg = format(msg, player, "player");
        if (target != null) msg = format(msg, target, "target");
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public Component format(String msg, Player player) {
        if (player != null) msg = format(msg, player, "player");
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public String formatStr(String msg, Player player, Player target) {
        if (player != null) msg = format(msg, player, "player");
        if (target != null) msg = format(msg, target, "target");
        return replaceColor(msg, mapColor());
    }

    public String formatStr(String msg, Player player) {
        if (player != null) msg = format(msg, player, "player");
        return replaceColor(msg, mapColor());
    }

    private String format(String msg, Player player, String tag) {
        if (msg == null)
            return "";
        if (player != null) {
            Map<String, String> mappedPlaceholder = mapPlaceholder(player, tag);
            msg = replacePlaceholders(msg, mappedPlaceholder);
            if (PlaceholderAPIHook.isEnabled()) {
                PlaceholderAPI.setPlaceholders(player, msg);
            }
        }
        return msg;
    }

    private String replaceColor(String msg, Map<String, String> mappedColors) {
        for (Map.Entry<String, String> entry : mappedColors.entrySet()) {
            msg = msg.replace(entry.getKey(), entry.getValue());
        }
        return msg;
    }

    private String replacePlaceholders(String msg, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            msg = msg.replace(entry.getKey(), entry.getValue());
        }
        return msg;
    }

    private Map<String, String> mapColor() {
        Map<String, String> mappedColors = new HashMap<>();
        mappedColors.put("<black>", "§0");
        mappedColors.put("<dark_blue>", "§1");
        mappedColors.put("<dark_green>", "§2");
        mappedColors.put("<dark_aqua>", "§3");
        mappedColors.put("<dark_red>", "§4");
        mappedColors.put("<dark_purple>", "§5");
        mappedColors.put("<gold>", "§6");
        mappedColors.put("<gray>", "§7");
        mappedColors.put("<dark_gray>", "§8");
        mappedColors.put("<blue>", "§9");
        mappedColors.put("<green>", "§a");
        mappedColors.put("<aqua>", "§b");
        mappedColors.put("<red>", "§c");
        mappedColors.put("<light_purple>", "§d");
        mappedColors.put("<yellow>", "§e");
        mappedColors.put("<white>", "§f");
        return mappedColors;
    }

    private Map<String, String> mapPlaceholder(Player player, String tag) {
        Map<String, String> placeholders = new HashMap<>();

        // Identità e visibilità
        placeholders.put("[" + tag + "Name]", player.getName());
        placeholders.put("[" + tag + "DisplayName]", player.getDisplayName());
        placeholders.put("[" + tag + "UUID]", player.getUniqueId().toString());
        placeholders.put("[" + tag + "IP]", player.getAddress() != null ? player.getAddress().getAddress().getHostAddress() : "N/A");

        // Posizione e mondo
        Location loc = player.getLocation();
        placeholders.put("[" + tag + "World]", player.getWorld().getName());
        placeholders.put("[" + tag + "LocationX]", String.format("%.2f", loc.getX()));
        placeholders.put("[" + tag + "LocationY]", String.format("%.2f", loc.getY()));
        placeholders.put("[" + tag + "LocationZ]", String.format("%.2f", loc.getZ()));
        placeholders.put("[" + tag + "Yaw]", String.format("%.2f", loc.getYaw()));
        placeholders.put("[" + tag + "Pitch]", String.format("%.2f", loc.getPitch()));

        // Stato del giocatore
        placeholders.put("[" + tag + "Health]", String.valueOf(player.getHealth()));
        placeholders.put("[" + tag + "MaxHealth]", String.valueOf(player.getMaxHealth()));
        placeholders.put("[" + tag + "FoodLevel]", String.valueOf(player.getFoodLevel()));
        placeholders.put("[" + tag + "Saturation]", String.valueOf(player.getSaturation()));
        placeholders.put("[" + tag + "IsSneaking]", String.valueOf(player.isSneaking()));
        placeholders.put("[" + tag + "IsSprinting]", String.valueOf(player.isSprinting()));
        placeholders.put("[" + tag + "IsSwimming]", String.valueOf(player.isSwimming()));
        placeholders.put("[" + tag + "IsFlying]", String.valueOf(player.isFlying()));
        placeholders.put("[" + tag + "IsGliding]", String.valueOf(player.isGliding()));
        placeholders.put("[" + tag + "GameMode]", player.getGameMode().toString());
        placeholders.put("[" + tag + "WalkSpeed]", String.valueOf(player.getWalkSpeed()));
        placeholders.put("[" + tag + "FlySpeed]", String.valueOf(player.getFlySpeed()));

        // Esperienza
        placeholders.put("[" + tag + "Level]", String.valueOf(player.getLevel()));
        placeholders.put("[" + tag + "Exp]", String.valueOf(player.getExp()));
        placeholders.put("[" + tag + "ExpToLevel]", String.valueOf(player.getExpToLevel()));

        // Inventario
        placeholders.put("[" + tag + "HeldItem]", getItemName(player.getInventory().getItemInMainHand()));
        placeholders.put("[" + tag + "HeldSlot]", String.valueOf(player.getInventory().getHeldItemSlot()));
        placeholders.put("[" + tag + "ArmorHelmet]", getItemName(player.getInventory().getHelmet()));
        placeholders.put("[" + tag + "ArmorChestplate]", getItemName(player.getInventory().getChestplate()));
        placeholders.put("[" + tag + "ArmorLeggings]", getItemName(player.getInventory().getLeggings()));
        placeholders.put("[" + tag + "ArmorBoots]", getItemName(player.getInventory().getBoots()));
        placeholders.put("[" + tag + "IsOp]", String.valueOf(player.isOp()));

        return placeholders;
    }

    private String getItemName(ItemStack item) {
        if (item == null || item.getType().isAir()) return "None";
        return item.getType().toString();
    }


}
