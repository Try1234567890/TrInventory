package me.tr.trInventory.managers.inventory.loaders;

import me.tr.configuration.Section;
import me.tr.trInventory.TrInventory;
import me.tr.trInventory.managers.inventory.loaders.types.TrAnvilInventoryLoader;
import me.tr.trInventory.managers.inventory.loaders.types.TrBaseInventoryLoader;
import me.tr.trInventory.managers.inventory.types.TrAnvilInventory;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class InventoryLoaderManager {
    private TrInventory main = TrInventory.getMain();

    public List<TrBaseInventory> loadAll(Plugin plugin, Section invsSection) {
        final List<TrBaseInventory> inventories = new ArrayList<>();
        for (String ID : invsSection.getKeys(false)) {
            Section section = invsSection.getSection(ID);
            TrBaseInventory inv = load(plugin, section);
            inventories.add(inv);
        }
        return inventories;
    }

    public TrBaseInventory load(Plugin plugin, Section invSection) {
        String id = invSection.getName();
        InventoryType inventoryType = main.getInventoryManager().getInventoryType(invSection);
        switch (inventoryType) {
            case DISPENSER:

                break;
            case DROPPER:

                break;
            case FURNACE:

                break;
            case WORKBENCH:

                break;
            case CRAFTING:

                break;
            case ENCHANTING:

                break;
            case BREWING:

                break;
            case PLAYER:

                break;
            case CREATIVE:

                break;
            case MERCHANT:

                break;
            case ENDER_CHEST:

                break;
            case ANVIL:
                TrAnvilInventory anvilInventory = new TrAnvilInventoryLoader(plugin).load(invSection);
                main.getInventoryManager().getCachedInventories().put(id, anvilInventory);
                return anvilInventory;
            case SMITHING:

                break;
            case BEACON:

                break;
            case HOPPER:

                break;
            case SHULKER_BOX:

                break;
            case BARREL:

                break;
            case BLAST_FURNACE:

                break;
            case LECTERN:

                break;
            case SMOKER:

                break;
            case LOOM:

                break;
            case CARTOGRAPHY:

                break;
            case GRINDSTONE:

                break;
            case STONECUTTER:

                break;
            case COMPOSTER:

                break;
            case CHISELED_BOOKSHELF:

                break;
            case JUKEBOX:

                break;
            case DECORATED_POT:

                break;
            case CRAFTER:

                break;
            default:
                TrBaseInventory baseInventory = new TrBaseInventoryLoader(plugin).load(invSection);
                main.getInventoryManager().getCachedInventories().put(id, baseInventory);
                return baseInventory;
        }
        return null;
    }
}
