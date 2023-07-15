package net.petersil98.scuttlegeist.util;

public class Utils {

    public static String padLeft(char c, int desiredSize, String s) {
        StringBuilder builder = new StringBuilder(s);
        while (builder.length() < desiredSize) {
            builder.insert(0, c);
        }
        return builder.toString();
    }
}
