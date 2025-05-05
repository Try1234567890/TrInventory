package me.tr.trInventory.managers.inventory.types;

import me.tr.trInventory.managers.chat.MessageFormatter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;

public class TrAnvilInventory extends TrBaseInventory {
    private String itemName;
    private boolean bypassEnchantmentLevelRestriction;
    private int maximumRepairCost;
    private int repairItemCountCost;
    private int cost;

    public TrAnvilInventory(TrBaseInventory inv) {
        super(inv.getPlugin(), inv.getId());
    }

    public TrAnvilInventory(TrBaseInventory inv, String itemName, boolean bypassEnchantmentLevelRestriction, int maximumRepairCost, int repairItemCountCost, int cost) {
        super(inv.getPlugin(), inv.getId());
        this.itemName = itemName;
        this.bypassEnchantmentLevelRestriction = bypassEnchantmentLevelRestriction;
        this.maximumRepairCost = maximumRepairCost;
        this.repairItemCountCost = repairItemCountCost;
        this.cost = cost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isBypassEnchantmentLevelRestriction() {
        return bypassEnchantmentLevelRestriction;
    }

    public void setBypassEnchantmentLevelRestriction(boolean bypassEnchantmentLevelRestriction) {
        this.bypassEnchantmentLevelRestriction = bypassEnchantmentLevelRestriction;
    }

    public int getMaximumRepairCost() {
        return maximumRepairCost;
    }

    public void setMaximumRepairCost(int maximumRepairCost) {
        this.maximumRepairCost = maximumRepairCost;
    }

    public int getRepairItemCountCost() {
        return repairItemCountCost;
    }

    public void setRepairItemCountCost(int repairItemCountCost) {
        this.repairItemCountCost = repairItemCountCost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public AnvilInventory build(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        int containerID = serverPlayer.nextContainerCounter();
        AnvilMenu anvilMenu = new AnvilMenu(containerID,
                serverPlayer.getInventory(),
                ContainerLevelAccess.create(serverPlayer.level(), BlockPos.ZERO)
        );
        anvilMenu.setItemName(getItemName());
        anvilMenu.getBukkitView().bypassEnchantmentLevelRestriction(isBypassEnchantmentLevelRestriction());
        anvilMenu.getBukkitView().setMaximumRepairCost(getMaximumRepairCost());
        anvilMenu.getBukkitView().setRepairItemCountCost(getRepairItemCountCost());
        anvilMenu.getBukkitView().setRepairCost(getCost());
        Component title = MessageFormatter.toNMS(main.getMessageFormatter().format(super.getTitle(), player, player));
        serverPlayer.connection.send(new ClientboundOpenScreenPacket(
                containerID,
                MenuType.ANVIL,
                title
        ));
        serverPlayer.containerMenu = anvilMenu;
        serverPlayer.connection.send(new ClientboundOpenScreenPacket(containerID, MenuType.ANVIL, title));
        return anvilMenu.getBukkitView().getTopInventory();
    }

}

