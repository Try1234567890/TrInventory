package me.tr.trInventory.managers.inventory.loaders.types;

import me.tr.configuration.Section;
import me.tr.trInventory.TrInventory;
import me.tr.trInventory.helper.animation.item.AnimatedItems;
import me.tr.trInventory.helper.animation.title.AnimatedTitle;
import me.tr.trInventory.helper.time.TimeUnit;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import me.tr.trItems.item.items.TrInventoryItem;
import me.tr.trItems.item.loaders.loader.TrInventoryItemLoader;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TrBaseInventoryLoader {
    protected TrInventory main = TrInventory.getMain();
    private Plugin plugin;

    public TrBaseInventoryLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public TrBaseInventory load(Section invSection) {
        final String id = invSection.getName();
        final int rows = getRows(invSection);
        final InventoryType inventoryType = getInventoryType(invSection);
        final List<TrInventoryItem> items = getItems(plugin, invSection);
        TrBaseInventory inventory = new TrBaseInventory(plugin, id);
        inventory.setItems(items);
        if (rows != -1) {
            inventory = load(invSection, inventory);
            inventory.setRows(rows);
        } else {
            inventory = load(invSection, inventory);
            inventory.setInventoryType(inventoryType);
        }
        return inventory;
    }

    private TrBaseInventory load(Section invSection, TrBaseInventory inventory) {
        if (isTitleAnimated(invSection))
            inventory.setAnimatedTitle(getTitleAnimated(invSection));
        else inventory.setTitle(getTitle(invSection));


        if (hasItemsAnimations(invSection))
            inventory.setAnimatedItems(getItemsAnimations(invSection));
        return inventory;
    }

    public InventoryType getInventoryType(Section invSection) {
        String invTypeStr = invSection.getString("Rows");
        try {
            return InventoryType.valueOf(invTypeStr);
        } catch (IllegalArgumentException e) {
            main.getTrLogger().warn("Invalid Inventory Type: " + invTypeStr);
            return null;
        }
    }

    public List<AnimatedItems> getItemsAnimations(Section invSection) {
        Section animationSection = invSection.getSection("Animation");
        final List<AnimatedItems> animatedItems = new ArrayList<>();
        final List<TrInventoryItem> items = new ArrayList<>();
        if (animationSection == null) return animatedItems;
        for (String itemSectionStr : animationSection.getKeys(false)) {
            Section itemSection = animationSection.getSection(itemSectionStr);
            TrInventoryItem inventoryItem = new TrInventoryItemLoader().load(plugin, itemSection);
            items.add(inventoryItem);
        }
        String interval = invSection.getString("Interval", "1s");
        TimeUnit timeUnit = main.getTimeManager().getTimeUnitFromString(interval);
        long ticks = (long) timeUnit.to(TimeUnit.TICK, main.getTimeManager().getTimeFromString(interval));
        AnimatedItems animatedItem = new AnimatedItems(items, ticks);
        animatedItems.add(animatedItem);
        return animatedItems;
    }

    public boolean hasItemsAnimations(Section invSection) {
        return invSection.getSection("Animations") != null;
    }


    public List<TrInventoryItem> getItems(Plugin plugin, Section invSection) {
        final List<TrInventoryItem> items = new ArrayList<>();
        Section itemsSection = invSection.getSection("Items");
        for (String itemID : itemsSection.getKeys(false)) {
            Section itemSection = itemsSection.getSection(itemID);
            if (itemSection == null)
                continue;
            TrInventoryItem inventoryItem = new TrInventoryItemLoader().load(plugin, itemSection);
            items.add(inventoryItem);
        }
        return items;
    }

    public String getTitle(Section invSection) {
        String titleStr = invSection.getString("Title");
        if (titleStr == null)
            return " ";
        return titleStr;
    }

    public @Nullable AnimatedTitle getTitleAnimated(Section invSection) {
        List<String> titlesStr = invSection.getStringList("Title");
        final List<String> titleAnimated = new ArrayList<>(titlesStr);
        long interval = getAnimatedTitleInterval(invSection);
        return new AnimatedTitle(titleAnimated, interval);
    }

    public long getAnimatedTitleInterval(Section invSection) {
        String interval = invSection.getString("Interval", "1s");
        TimeUnit timeUnit = main.getTimeManager().getTimeUnitFromString(interval);
        long time = main.getTimeManager().getTimeFromString(interval);
        return (long) timeUnit.to(TimeUnit.TICK, time);
    }

    public int getRows(Section invSection) {
        return invSection.getInt("Rows", -1);
    }

    public boolean isTitleAnimated(Section invSection) {
        return invSection.isList("Title") && !invSection.getStringList("Title").isEmpty();
    }

}
