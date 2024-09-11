package me.flame.communication.messages;

import net.kyori.adventure.text.Component;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.UUID;

@SuppressWarnings("unused")
public record Message(UUID sender, UUID recipient, String content) {}
