package me.flame.communication.utils;

import me.flame.communication.utils.strings.DefaultFontInfo;

import org.jetbrains.annotations.NotNull;

public class StringUtils {
    /**
     * Centers a string of text within a field of a given length.
     * This is done by adding spaces equally to the left and right
     * of the string until it is the given length.
     *
     * @param text the string to center
     * @return the centered string
     */
    @NotNull
    public static String center(@NotNull String text) {
        return DefaultFontInfo.center(text, true, false, false);
    }
}
