package me.flame.communication.data;

import org.bukkit.entity.Player;

public interface RawDataRegistry {
    Player getPlayer();

    String getMessage();

    void setMessage(String message);

    void addRegistration(String key, Object value);

    <E> E getRegistration(String key);

    <E> E getRegistrationOr(String key, E defaultValue);

    int getRegistrationsSize();
}
