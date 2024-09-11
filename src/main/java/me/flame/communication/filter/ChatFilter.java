package me.flame.communication.filter;

public interface ChatFilter {
    void prohibitWord(String word);

    void allowWord(String word);

    boolean isMessageBlocked(String message);
    //boolean isMessageCensored(String message);
}
