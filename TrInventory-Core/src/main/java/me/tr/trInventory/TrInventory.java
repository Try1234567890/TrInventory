package me.tr.trInventory;

import me.tr.TrFiles;
import me.tr.trInventory.helper.time.TimeManager;
import me.tr.trInventory.managers.chat.MessageFormatter;
import me.tr.trInventory.managers.inventory.InventoryManager;
import me.tr.trInventory.managers.inventory.loaders.InventoryLoaderManager;
import me.tr.trItems.TrItems;
import me.tr.trLogger.TrLogger;

public final class TrInventory {
    private static TrInventory main;
    private MessageFormatter messageFormatter;
    private TrLogger trLogger;
    private TrFiles trFiles;
    private TrItems trItems;
    private InventoryLoaderManager inventoryLoaderManager;
    private InventoryManager inventoryManager;
    private TimeManager timeManager;

    public TrInventory() {
        init();
    }

    private void init() {
        main = this;
        messageFormatter = new MessageFormatter();
        trLogger = new TrLogger();
        trFiles = new TrFiles();
        trItems = new TrItems();
        inventoryLoaderManager = new InventoryLoaderManager();
        inventoryManager = new InventoryManager();
        timeManager = new TimeManager();
    }

    public static TrInventory getMain() {
        return main;
    }

    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }

    public TrLogger getTrLogger() {
        return trLogger;
    }

    public TrFiles getTrFiles() {
        return trFiles;
    }

    public TrItems getTrItems() {
        return trItems;
    }

    public InventoryLoaderManager getInventoryLoaderManager() {
        return inventoryLoaderManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }
}
