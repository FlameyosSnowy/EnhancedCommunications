package me.flame.communication.filter.ai;

import java.util.concurrent.CompletableFuture;

public interface AiChatFilter {
    void prohibitWord(String word);

    void allowWord(String word);

    CompletableFuture<Integer> isMessageBlocked(String message);
}
