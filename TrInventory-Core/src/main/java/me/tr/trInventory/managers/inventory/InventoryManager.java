package me.tr.trInventory.managers.inventory;

import me.tr.configuration.Section;
import me.tr.trInventory.TrInventory;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private TrInventory main = TrInventory.getMain();
    private Map<String, TrBaseInventory> cachedInventories = new HashMap<>();

    public Map<String, TrBaseInventory> getCachedInventories() {
        return cachedInventories;
    }

    public InventoryType getInventoryType(Section invSection) {
        String typeStr = invSection.getString("Type");
        if (typeStr == null)
            return InventoryType.CHEST;
        try {
            return InventoryType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            main.getTrLogger().warn("Inventory type " + typeStr + " not recognized.");
            return InventoryType.CHEST;
        }
    }

    public @Nullable TrBaseInventory getInventory(String ID) {
        if (getCachedInventories().containsKey(ID))
            return getCachedInventories().get(ID);
        return null;
    }

}
