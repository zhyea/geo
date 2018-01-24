package org.chobit.geo.tools;

public class ArgsChecker {


    public static void check(boolean result, String errorMessage) {
        if (!result)
            throw new IllegalArgumentException(errorMessage);
    }

    private ArgsChecker() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}
