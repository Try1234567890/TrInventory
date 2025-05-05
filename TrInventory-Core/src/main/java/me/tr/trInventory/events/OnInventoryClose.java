package me.tr.trInventory.events;

import me.tr.trInventory.TrInventory;
import me.tr.trInventory.helper.animation.item.AnimatedItems;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class OnInventoryClose implements Listener {
    private TrInventory main = TrInventory.getMain();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player player) {
            Inventory currentInventory = e.getInventory();
            for (TrBaseInventory inventory : main.getInventoryManager().getCachedInventories().values()) {
                if (!inventory.isTitleAnimated() && !inventory.hasItemsAnimations())
                    return;
                if (currentInventory == inventory.getInventory()) {
                    inventory.getAnimatedTitle().start(player);
                    for (AnimatedItems animatedItems : inventory.getAnimatedItems()) {
                        animatedItems.start(player);
                    }
                }
            }
        }
    }

}
