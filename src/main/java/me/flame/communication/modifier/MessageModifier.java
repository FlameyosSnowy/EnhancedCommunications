package me.flame.communication.modifier;

import me.flame.communication.data.RawDataRegistry;

public interface MessageModifier {
    ModifierPriority getPriority();

    String modify(RawDataRegistry dataRegistry);
}
