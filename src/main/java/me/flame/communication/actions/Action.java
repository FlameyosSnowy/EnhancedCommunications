package me.flame.communication.actions;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public interface Action {
    MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    /**
     * Check how many steps/arguments is expected to come from data registry.
     * @apiNote if the args is lower than required, we throw, but if it's not then it could mean we're processing extra steps, such as PAPI placeholder setting.
     * @return the expected args
     */
    int expectedArgs();

    void execute(Player involvedPlayer);
}
