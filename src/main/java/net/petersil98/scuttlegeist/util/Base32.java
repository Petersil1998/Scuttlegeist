package net.petersil98.scuttlegeist.util;

import java.util.HashMap;
import java.util.Map;

public class Base32 {

    private static final char[] DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
    private static final int MASK = DIGITS.length - 1;
    private static final int SHIFT = Integer.numberOfTrailingZeros(DIGITS.length);
    private static final Map<Character, Integer> CHAR_MAP = new HashMap<>();
    private static final String SEPARATOR = "-";

    static {
        for (int i = 0; i < DIGITS.length; i++) CHAR_MAP.put(DIGITS[i], i);
    }

    public static byte[] decode(String code) {
        String encoded = code.trim().replace(SEPARATOR, "").replaceAll("=*$", "").toUpperCase();

        if (encoded.length() == 0) {
            return new byte[]{0};
        }

        byte[] result = new byte[encoded.length() * SHIFT / 8];

        int buffer = 0;
        int next = 0;
        int bitsLeft = 0;

        for (char c : encoded.toCharArray()) {
            if (!CHAR_MAP.containsKey(c)) throw new IllegalArgumentException("Illegal character: " + c);

            buffer <<= SHIFT;
            buffer |= CHAR_MAP.get(c) & MASK;
            bitsLeft += SHIFT;

            if (bitsLeft >= 8) {
                result[next++] = (byte) (buffer >>> (bitsLeft - 8));
                bitsLeft -= 8;
            }
        }
        return result;
    }


    public static String encode(byte[] data) {
        if (data.length == 0) {
            return "";
        }

        if (data.length >= (1 << 28)) {
            throw new IllegalArgumentException("Array is too long to be encoded");
        }

        StringBuilder result = new StringBuilder((data.length * 8 + SHIFT - 1) / SHIFT);

        int buffer = data[0];
        int next = 1;
        int bitsLeft = 8;
        while (bitsLeft > 0 || next < data.length) {
            if (bitsLeft < SHIFT) {
                if (next < data.length) {
                    buffer <<= 8;
                    buffer |= (data[next++] & 0xff);
                    bitsLeft += 8;
                } else {
                    int pad = SHIFT - bitsLeft;
                    buffer <<= pad;
                    bitsLeft += pad;
                }
            }
            int index = MASK & (buffer >> (bitsLeft - SHIFT));
            bitsLeft -= SHIFT;
            result.append(DIGITS[index]);
        }
        return result.toString();
    }
}
