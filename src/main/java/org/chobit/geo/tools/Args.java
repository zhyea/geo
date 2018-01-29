package org.chobit.geo.tools;

public class Args {


    public static void check(boolean result, String errorMessage) {
        if (result)
            throw new IllegalArgumentException(errorMessage);
    }

    private Args() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}
