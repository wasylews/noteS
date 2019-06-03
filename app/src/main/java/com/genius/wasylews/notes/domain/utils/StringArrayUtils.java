package com.genius.wasylews.notes.domain.utils;

import java.util.Arrays;

public class StringArrayUtils {

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean equals(char[] a, char[] b) {
        return Arrays.equals(a, b);
    }

    public static boolean equals(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }

    public static String toString(char[] a) {
        return new String(a);
    }

    public static String toString(byte[] a) {
        return new String(a);
    }
}
