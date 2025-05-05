package me.tr.trInventory.helper.animation;

import me.tr.trInventory.TrInventory;
import me.tr.trInventory.managers.inventory.types.TrBaseInventory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public abstract class Animation {
    protected TrInventory main = TrInventory.getMain();
    private Plugin plugin;
    private TrBaseInventory inventory;
    private BukkitTask task;
    private List<?> listToAnimate = new ArrayList<>();
    private long interval = 1;

    public Animation(Plugin plugin, TrBaseInventory inventory, List<?> listToAnimate, long interval) {
        this.plugin = plugin;
        this.inventory = inventory;
        this.listToAnimate = listToAnimate;
        this.interval = interval;
    }

    public Animation(Plugin plugin, TrBaseInventory inventory, List<?> listToAnimate) {
        this.plugin = plugin;
        this.inventory = inventory;
        this.listToAnimate = listToAnimate;
    }

    public Animation(Plugin plugin, TrBaseInventory inventory) {
        this.plugin = plugin;
        this.inventory = inventory;
    }

    protected Animation() {

    }

    public Plugin getPlugin() {
        return plugin;
    }

    public TrBaseInventory getInventory() {
        return inventory;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public List<?> getListToAnimate() {
        return listToAnimate;
    }

    public void stop() {
        getTask().cancel();
    }

    public void setListToAnimate(List<?> listToAnimate) {
        this.listToAnimate = listToAnimate;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public abstract void start(Player player);
}
