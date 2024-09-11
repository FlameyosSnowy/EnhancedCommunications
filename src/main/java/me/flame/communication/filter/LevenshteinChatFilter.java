package me.flame.communication.filter;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LevenshteinChatFilter implements ChatFilter {
    private final Set<String> bannedWords = new HashSet<>();
    private final Map<Character, String> SUBSTITUTION_MAP = new HashMap<>();
    private static final int LEVENSHTEIN_THRESHOLD = 2;  // Adjust the threshold based on tolerance

    public LevenshteinChatFilter() {
        // Initialize substitution map
        SUBSTITUTION_MAP.put('a', "a@4");
        SUBSTITUTION_MAP.put('e', "e3");
        SUBSTITUTION_MAP.put('i', "i1!");
        SUBSTITUTION_MAP.put('o', "o0");
        SUBSTITUTION_MAP.put('u', "uµ");
        SUBSTITUTION_MAP.put('s', "s5$");
        SUBSTITUTION_MAP.put('t', "t7+");
        SUBSTITUTION_MAP.put('c', "c(k|\\(|\\{)");
        SUBSTITUTION_MAP.put('k', "k(q|\\(|\\{)");
        SUBSTITUTION_MAP.put('b', "b8");
        SUBSTITUTION_MAP.put('l', "l1!|\\|");
        SUBSTITUTION_MAP.put('g', "g9");
        // Add more substitutions as needed

        // List of banned words
        String[] wordList = {"fuck", "shit", "bitch", "slut"};
        Collections.addAll(bannedWords, wordList);
    }

    @NotNull
    private String normalize(@NotNull String word) {
        StringBuilder normalizedWord = new StringBuilder();
        for (int characterIndex = 0; characterIndex < word.length(); characterIndex++) {
            char character = word.charAt(characterIndex);
            String regexPart = generateFlexiblePattern(character);
            normalizedWord.append(regexPart);
        }
        return normalizedWord.toString();
    }

    @NotNull
    private String generateFlexiblePattern(char character) {
        char lowerCaseCharacter = Character.toLowerCase(character);
        String substitution = SUBSTITUTION_MAP.getOrDefault(lowerCaseCharacter, String.valueOf(lowerCaseCharacter));
        return "[" + substitution + Character.toUpperCase(lowerCaseCharacter) + "]";
    }

    public boolean isMessageBlocked(String message) {
        LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
        String normalizedMessage = normalize(message);

        for (String bannedWord : bannedWords) {
            int distance = levenshteinDistance.apply(normalizedMessage, bannedWord);
            if (distance <= LEVENSHTEIN_THRESHOLD) {
                return true;
            }
        }

        return false;
    }

    public void prohibitWord(String word) {
        bannedWords.add(word);
    }

    public void allowWord(String word) {
        bannedWords.remove(word);
    }
}