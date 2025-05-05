package me.tr.trInventory.managers.inventory.loaders.types;

import me.tr.configuration.Section;
import me.tr.trInventory.managers.inventory.types.TrAnvilInventory;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import org.bukkit.plugin.Plugin;

public class TrAnvilInventoryLoader extends TrBaseInventoryLoader {

    public TrAnvilInventoryLoader(Plugin plugin) {
        super(plugin);
    }

    public TrAnvilInventory load(Section invSection) {
        TrBaseInventory inventory = super.load(invSection);
        String itemName = getItemName(invSection);
        boolean bypassEnchantmentLevelRestriction = getBypassEnchantmentLevelRestriction(invSection);
        int maximumRepairCost = getMaxRepairCost(invSection);
        int repairItemCountCost = getRepairItemCountCost(invSection);
        int cost = getCost(invSection);
        TrAnvilInventory anvilInventory = new TrAnvilInventory(inventory);
        anvilInventory.setItemName(itemName);
        anvilInventory.setBypassEnchantmentLevelRestriction(bypassEnchantmentLevelRestriction);
        anvilInventory.setMaximumRepairCost(maximumRepairCost);
        anvilInventory.setRepairItemCountCost(repairItemCountCost);
        anvilInventory.setCost(cost);
        return anvilInventory;
    }

    public String getItemName(Section invSection) {
        return invSection.getString("Name");
    }

    public boolean getBypassEnchantmentLevelRestriction(Section invSection) {
        return invSection.getBoolean("BypassEnchantmentLevelRestriction", invSection.getBoolean("BELR"));
    }

    public int getMaxRepairCost(Section invSection) {
        return invSection.getInt("MaxRepairCost");
    }

    public int getRepairItemCountCost(Section invSection) {
        return invSection.getInt("RepairItemCountCost");
    }

    public int getCost(Section invSection) {
        return invSection.getInt("Cost");
    }


}
