package me.flame.communication.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class MentionPlayerEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player mentioner, mentioned;

    public MentionPlayerEvent(final Player mentioner, final Player mentioned) {
        this.mentioner = mentioner;
        this.mentioned = mentioned;
    }

    public MentionPlayerEvent(final boolean async, final Player mentioner, final Player mentioned) {
        super(async);
        this.mentioner = mentioner;
        this.mentioned = mentioned;
    }

    public Player getMentioned() {
        return mentioned;
    }

    public Player getMentioner() {
        return mentioner;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlersList() {
        return HANDLER_LIST;
    }
}