package me.flame.communication.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DataRegistryImpl implements DataRegistry {
    private final Map<String, Object> registry = new HashMap<>();

    public void addRegistration(String key, Object value) {
        this.registry.put(key, value);
    }

    public <E> E getRegistration(String key) {
        return (E) this.registry.get(key);
    }

    public <E> E getRegistrationOr(String key, E defaultValue) {
        E value = (E) this.registry.get(key);
        return value == null ? defaultValue : value;
    }

    public int getRegistrationsSize() {
        return this.registry.size();
    }
}
