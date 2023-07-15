package net.petersil98.scuttlegeist.util;

import java.util.Arrays;
import java.util.List;

public class VarInt {
    private static final byte ALL_BUT_MSB = 0x7f;
    private static final int JUST_MSB = 0x80;

    public static int pop(List<Byte> bytes) {
        int result = 0;
        int currentShift = 0;
        int bytesPopped = 0;

        for (int i = 0; i < bytes.size(); i++) {
            bytesPopped++;
            result |= ((long) bytes.get(i) & ALL_BUT_MSB) << currentShift;

            if ((bytes.get(i) & JUST_MSB) != JUST_MSB) {
                bytes.subList(0, bytesPopped).clear();
                return result;
            }

            currentShift += 7;

        }
        throw new IllegalArgumentException("Byte array did not contain valid varints.");
    }

    public static List<Integer> get(int value) {
        if (value == 0) return List.of(0);

        Integer[] buff = new Integer[10];
        Arrays.fill(buff, 0);
        int currentIndex = 0;

        while (value != 0) {
            int byteVal = value & ALL_BUT_MSB;
            value >>>= 7;

            if (value != 0) byteVal |= JUST_MSB;

            buff[currentIndex++] = byteVal;
        }
        return Arrays.asList(Arrays.copyOfRange(buff, 0, currentIndex));
    }
}