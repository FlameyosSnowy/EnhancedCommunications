package me.flame.communication.modifier;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.providers.ChatProvider;
import org.bukkit.event.EventPriority;

public class ChatFormatMessageModifier implements MessageModifier {
    @Override
    public EventPriority getPriority() {
        return EventPriority.HIGHEST;
    }

    @Override
    public String modify(final RawDataRegistry dataRegistry) {
        ChatProvider provider = EnhancedCommunication.get()
                .getChatManager()
                .getChatProvider();
        String format = EnhancedCommunication.get()
                .getPrimaryConfig()
                .getGroupFormat();
        return provider.getFormat(dataRegistry.getMessage(), format, dataRegistry.getPlayer());
    }
}
