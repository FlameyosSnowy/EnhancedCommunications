package me.flame.communication.messages;

import java.util.UUID;

@SuppressWarnings("unused")
public record Message(UUID sender, UUID recipient, String content) {}
