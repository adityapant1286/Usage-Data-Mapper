package com.zuora.usagedatamapper.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class AppUtil {
    private AppUtil() {}

    public static boolean isEmpty(Object o) {
        boolean empty = Objects.isNull(o);

        if (!empty && o instanceof String)
            empty = o.toString().isEmpty();

        return empty;
    }

    public static boolean isAllEmpty(Object... o) {
        boolean empty = isEmpty(o);

        if (!empty) {
            empty = Arrays.stream(o).allMatch(AppUtil::isEmpty);
        }
        return empty;
    }

    public static boolean isAnyEmpty(Object... o) {
        boolean empty = isEmpty(o);

        if (!empty) {
            empty = Arrays.stream(o).anyMatch(AppUtil::isEmpty);
        }
        return empty;
    }

    public static String[] commonExclusionFields() {
        return (String[]) List.of("id", "createdDate", "updatedDate")
                                .toArray();
    }

    public static String formatStr(String f, Object... args) {
        return String.format(f, args);
    }
}
