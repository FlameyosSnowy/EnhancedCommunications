package me.flame.communication.actions;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ActionsRegistry implements AutoCloseable {
    private final List<Action> actions;

    public ActionsRegistry(final int size) {
        this.actions = new ArrayList<>(size);
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void executeActions(Player player) {
        for (int actionIndex = 0; actionIndex < actions.size(); actionIndex++) {
            this.actions.get(actionIndex).execute(player);
        }
    }

    @Override
    public void close() {
        this.actions.clear();
    }
}
