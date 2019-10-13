package com.tyhgg.core.framework.util;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.tyhgg.core.framework.util.ByteUtil;

public class ByteUtilTest {
	
	@Test
	public void testDummyByteArray() {
		byte[] bytes = ArrayUtils.EMPTY_BYTE_ARRAY;
		int length = 0;
		byte pad = 1;
		
		Assert.assertArrayEquals(bytes, ByteUtil.dummyByteArray(length, pad));
		
		length = 10;
		bytes = new byte[]{pad,pad,pad,pad,pad,pad,pad,pad,pad,pad};
		Assert.assertArrayEquals(bytes, ByteUtil.dummyByteArray(length, pad));
	}
	
	@Test
	public void testLeftPad() {
		byte[] array = new byte[]{1,2,3,4,5};
		int length = 3;
		byte pad = 6;
		
		Assert.assertArrayEquals(array,(ByteUtil.leftPad(array, length, pad)));
		
		length = 10;
		byte[] array2 = new byte[]{6, 6, 6, 6, 6, 1, 2, 3, 4, 5};
		Assert.assertArrayEquals(array2,ByteUtil.leftPad(array, length, pad));
	}
	
	@Test
	public void testRightPad() {
		byte[] array = new byte[]{1,2,3,4,5};
		int length = 3;
		byte pad = 6;
		
		Assert.assertArrayEquals(array,(ByteUtil.rightPad(array, length, pad)));
		
		length = 10;
		byte[] array2 = new byte[]{1, 2, 3, 4, 5, 6, 6, 6, 6, 6};
		Assert.assertArrayEquals(array2,ByteUtil.rightPad(array, length, pad));
	}
	
	@Test
	public void testLeftPadZero() {
		byte[] array = new byte[]{1,2,3,4,5};
		int length = 3;
		Assert.assertArrayEquals(array,(ByteUtil.leftPadZero(array, length)));
		
		length = 10;
		byte[] array2 = new byte[]{48, 48, 48, 48 , 48, 1, 2, 3, 4, 5};
		Assert.assertArrayEquals(array2,ByteUtil.leftPadZero(array, length));
	}
	
}
