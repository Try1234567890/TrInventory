package me.tr.trInventory.managers.inventory.types;

import me.tr.trInventory.TrInventory;
import me.tr.trInventory.helper.animation.item.AnimatedItems;
import me.tr.trInventory.helper.animation.title.AnimatedTitle;
import me.tr.trItems.item.items.TrInventoryItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class TrBaseInventory {
    protected TrInventory main = TrInventory.getMain();
    private Plugin plugin;
    private String id;
    private Inventory inventory;
    private String title = " ";
    private int rows = -1;
    private InventoryType inventoryType = InventoryType.CHEST;
    private AnimatedTitle animatedTitle = new AnimatedTitle(new ArrayList<>());
    private List<AnimatedItems> animatedItems = new ArrayList<>();
    private List<TrInventoryItem> items = new ArrayList<>();
    private boolean titleAnimated = false;
    private boolean itemsAnimations = false;



    public TrBaseInventory(Plugin plugin, String id) {
        this.plugin = plugin;
        this.id = id;
    }


    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public AnimatedTitle getAnimatedTitle() {
        return animatedTitle;
    }

    public void setAnimatedTitle(AnimatedTitle animatedTitle) {
        setTitleAnimated(true);
        this.animatedTitle = animatedTitle;
    }

    public List<AnimatedItems> getAnimatedItems() {
        return animatedItems;
    }

    public void setAnimatedItems(List<AnimatedItems> animatedItems) {
        setItemsAnimations(true);
        this.animatedItems = animatedItems;
    }

    public List<TrInventoryItem> getItems() {
        return items;
    }

    public void setItems(List<TrInventoryItem> items) {
        this.items = items;
    }

    public boolean isTitleAnimated() {
        return titleAnimated;
    }

    public void setTitleAnimated(boolean titleAnimated) {
        this.titleAnimated = titleAnimated;
    }

    public boolean hasItemsAnimations() {
        return itemsAnimations;
    }

    public void setItemsAnimations(boolean itemsAnimations) {
        this.itemsAnimations = itemsAnimations;
    }

    public void setItem(TrInventoryItem item, Player player) {
        if (inventory == null)
            throw new NullPointerException("Inventory cannot be null");
        item.setMeta(player);
        getInventory().setItem(item.getSlot(), item.getStack());
    }

    public void removeItem(TrInventoryItem item) {
        if (inventory == null)
            throw new NullPointerException("Inventory cannot be null");
        getInventory().setItem(item.getSlot(), null);
    }

    public Inventory build(Player player) {
        if (getRows() != -1 && (getRows() < 1 || getRows() > 9)) setRows(1);
        if (isTitleAnimated()) {
            Component title = main.getMessageFormatter().format(getAnimatedTitle().getTitles().getFirst(), player, player);
            inventory = getRows() != -1 ? Bukkit.createInventory(null, getRows() * 9, title) : Bukkit.createInventory(null, getInventoryType(), title);
            getAnimatedTitle().start(player);
        } else {
            Component title = main.getMessageFormatter().format(getTitle(), player, player);
            inventory = getRows() != -1 ? Bukkit.createInventory(null, getRows() * 9, title) : Bukkit.createInventory(null, getInventoryType(), title);
        }
        setItems(player);
        return inventory;
    }

    public void setItems(Player player) {
        for (TrInventoryItem item : getItems()) {
            item.setMeta(player);
            getInventory().setItem(item.getSlot(), item.getStack());
        }
    }
}
