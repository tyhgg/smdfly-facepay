package com.tyhgg.core.framework.util;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author zyt5668
 *
 */
public class ByteUtil {
    public static final byte EMPTY_BYTE = 32;
    public static final byte ZERO_BYTE = 48;

    public static byte[] dummyByteArray(int length, byte pad) {
        if (length <= 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        byte[] res = new byte[length];
        for (int i = 0; i < res.length; i++) {
            res[i] = pad;
        }
        return res;
    }

    public static byte[] leftPad(byte[] array, int length) {
        return ByteUtil.leftPad(array, length, ByteUtil.EMPTY_BYTE);
    }

    public static byte[] leftPadZero(byte[] array, int length) {
        return ByteUtil.leftPad(array, length, ByteUtil.ZERO_BYTE);
    }
    
    public static byte[] leftPad(byte[] array, int length, byte pad) {
        if (array.length >= length) {
            return array;
        }
        return ArrayUtils.addAll(
                ByteUtil.dummyByteArray(length - array.length, pad), array);
    }

    public static byte[] rightPad(byte[] array, int length) {
        return ByteUtil.rightPad(array, length, ByteUtil.EMPTY_BYTE);
    }

    public static byte[] rightPad(byte[] array, int length, byte pad) {
        if (array.length >= length) {
            return array;
        }
        return ArrayUtils.addAll(array,
                ByteUtil.dummyByteArray(length - array.length, pad));
    }
}