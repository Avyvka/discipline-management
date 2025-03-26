package com.github.avyvka.discipline_management.support;

import java.util.Objects;
import java.util.UUID;

public final class CompactId {

    private final Class<?> type;

    private final String hexPart;

    private CompactId(Class<?> type, String hexPart) {
        this.type = type;
        this.hexPart = hexPart;
    }

    public static CompactId randomId(Class<?> type) {
        return new CompactId(type, UUID.randomUUID().toString().substring(0, 8));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CompactId compactId)) {
            return false;
        }

        return Objects.equals(type, compactId.type) && Objects.equals(hexPart, compactId.hexPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, hexPart);
    }

    @Override
    public String toString() {
        return type.getSimpleName() + "@" + hexPart;
    }
}
