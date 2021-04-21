package com.matheus.dias.tasklist.core;

import java.util.Objects;

public class StringUtils {

    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || text.isEmpty();
    }
}
