package me.flame.communication.data.impl;

import me.flame.communication.data.DataRegistry;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DataRegistryImpl implements DataRegistry {
    private Map<String, Object> registry;

    private Map<String, Object> getRegistry() {
        return (registry == null) ? (registry = new HashMap<>(3)) : registry;
    }

    public void addRegistration(String key, Object value) {
        this.getRegistry().put(key, value);
    }

    public <E> E getRegistration(String key) {
        if (registry == null) return null;
        return (E) this.registry.get(key);
    }

    public <E> E getRegistrationOr(String key, E defaultValue) {
        if (registry == null) return defaultValue;

        E value = (E) this.registry.get(key);
        return value == null ? defaultValue : value;
    }

    public int getRegistrationsSize() {
        if (registry == null) return 0;
        return this.registry.size();
    }
}
