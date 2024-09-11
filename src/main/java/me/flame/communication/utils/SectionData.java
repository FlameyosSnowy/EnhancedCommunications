package me.flame.communication.utils;

import java.util.Arrays;

public final class SectionData {
    private final Object[] sectionData;

    SectionData(final Object[] sectionData) {
        this.sectionData = sectionData;
    }

    public static SectionData create(Object... objects) {
        return new SectionData(objects);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final SectionData that = (SectionData) object;
        return Arrays.equals(sectionData, that.sectionData);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(sectionData);
    }
}
