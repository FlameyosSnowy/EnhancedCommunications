package me.flame.communication.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public interface DataRegistry {
    class Lazy {
        static final DataRegistry EMPTY = new EmptyDataRegistryImpl();
    }

    static DataRegistry create() {
        return new DataRegistryImpl();
    }

    static DataRegistry empty() {
        return Lazy.EMPTY;
    }

    void addRegistration(String key, Object value);

    <E> E getRegistration(String key);

    <E> E getRegistrationOr(String key, E defaultValue);

    int getRegistrationsSize();

    class EmptyDataRegistryImpl implements DataRegistry {
        @Override
        public void addRegistration(final String key, final Object value) {

        }

        @Override
        public <E> E getRegistration(final String key) {
            return null;
        }

        @Override
        public <E> E getRegistrationOr(final String key, final E defaultValue) {
            return null;
        }

        @Override
        public int getRegistrationsSize() {
            return 0;
        }
    }
}
