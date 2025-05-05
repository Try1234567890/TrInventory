package me.tr.trInventory.managers.inventory;

import me.tr.configuration.Section;
import me.tr.trInventory.TrInventory;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private TrInventory main = TrInventory.getMain();
    private List<TrBaseInventory> cachedInventories = new ArrayList<>();

    public List<TrBaseInventory> getCachedInventories() {
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
        for (TrBaseInventory inv : getCachedInventories()) {
            if (inv.getId().equals(ID)) {
                return inv;
            }
        }
        return null;
    }

}
