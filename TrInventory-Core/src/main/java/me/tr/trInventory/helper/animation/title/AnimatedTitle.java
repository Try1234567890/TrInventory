package me.tr.trInventory.helper.animation.title;

import me.tr.trInventory.helper.animation.Animation;
import me.tr.trInventory.managers.chat.MessageFormatter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimatedTitle extends Animation {

    @SuppressWarnings("unchecked")
    public List<String> getTitles() {
        return (List<String>) getListToAnimate();
    }

    public AnimatedTitle(List<String> titles) {
        setListToAnimate(titles);
    }

    public AnimatedTitle(List<String> titles, long interval) {
        setListToAnimate(titles);
        setInterval(interval);
    }

    @Override
    public void start(Player player) {
        setTask(new BukkitRunnable() {
            int index = 0;

            public void run() {
                List<String> titles = getTitles();
                if (titles.isEmpty())
                    return;
                if (index >= titles.size())
                    index = 0;
                Component title = MessageFormatter.toNMS(main.getMessageFormatter().format(titles.get(index), player, player));
                ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
                int containerId = serverPlayer.containerMenu.containerId;
                MenuType<?> menuType = serverPlayer.containerMenu.getType();
                serverPlayer.connection.send(new ClientboundOpenScreenPacket(containerId, menuType, title));
                serverPlayer.connection.send(new ClientboundContainerSetContentPacket(containerId, serverPlayer.containerMenu.getStateId(), serverPlayer.containerMenu.getItems(), serverPlayer.containerMenu.getCarried()));
                index++;
            }
        }.runTaskTimer(getPlugin(), 0, getInterval()));
    }


}
