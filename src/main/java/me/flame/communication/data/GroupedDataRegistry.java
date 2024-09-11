package me.flame.communication.data;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;

public interface GroupedDataRegistry extends MessageDataRegistry, Iterable<Player> {
    void add(@NotNull Player player);

    void remove(@NotNull Player player);

    boolean contains(@NotNull Player player);

    class Lazy {
        private static final GroupedDataRegistry EMPTY = new EmptyGroupedDataRegistry();
    }

    static GroupedDataRegistry empty() {
        return Lazy.EMPTY;
    }

    class EmptyGroupedDataRegistry implements GroupedDataRegistry {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void add(@NotNull final Player player) {

        }

        @Override
        public void remove(@NotNull final Player player) {

        }

        @Override
        public boolean contains(@NotNull final Player player) {
            return false;
        }

        @Override
        public String[] getWords() {
            return new String[0];
        }

        @Override
        public Player getPlayer() {
            return null;
        }

        @Override
        public String getMessage() {
            return "";
        }

        @Override
        public void setMessage(final String message) {

        }

        @Override
        public void addRegistration(final String key, final Object value) {

        }

        @Override
        public <E> E getRegistration(final String key) {
            return null;
        }

        @Override
        public <E> E getRegistrationOr(final String key, final E defaultValue) {
            return defaultValue;
        }

        @Override
        public int getRegistrationsSize() {
            return 0;
        }

        @NotNull
        @Override
        public Iterator<Player> iterator() {
            return Collections.emptyIterator();
        }
    }
}
