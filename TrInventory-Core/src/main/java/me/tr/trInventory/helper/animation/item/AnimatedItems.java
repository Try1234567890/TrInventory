package me.tr.trInventory.helper.animation.item;

import me.tr.trInventory.helper.animation.Animation;
import me.tr.trItems.item.items.TrInventoryItem;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimatedItems extends Animation {

    @Override
    public void start(Player player) {
        setTask(
                new BukkitRunnable() {
                    int index = 0;

                    public void run() {
                        final List<TrInventoryItem> items = getItems();
                        if (items.isEmpty()) return;
                        if (index >= items.size())
                            index = 0;
                        final TrInventoryItem currentItem = items.get(index);
                        final TrInventoryItem item = index != 0 ? items.get(index - 1) : currentItem;
                        getInventory().setItem(currentItem, player);
                        getInventory().removeItem(item);
                        index++;
                    }
                }.runTaskTimer(getPlugin(), 0, getInterval())
        );
    }

    @SuppressWarnings("unchecked")
    public List<TrInventoryItem> getItems() {
        return (List<TrInventoryItem>) getListToAnimate();
    }

    public AnimatedItems(List<TrInventoryItem> items) {
        setListToAnimate(items);
    }

    public AnimatedItems(List<TrInventoryItem> items, long interval) {
        setListToAnimate(items);
        setInterval(interval);
    }
}
