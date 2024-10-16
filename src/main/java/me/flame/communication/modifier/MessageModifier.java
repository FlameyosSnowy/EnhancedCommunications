package me.flame.communication.modifier;

import me.flame.communication.data.RawDataRegistry;
import org.bukkit.event.EventPriority;

public interface MessageModifier {
    EventPriority getPriority();

    String modify(RawDataRegistry dataRegistry);
}
