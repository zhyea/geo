package org.chobit.geo.tools;

/**
 * @author robin
 */
public class Args {


    public static void check(boolean result, String errorMessage) {
        if (result) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkNotNull(Object source, String errorMessage) {
        check(null == source, errorMessage);
    }

    private Args() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}
