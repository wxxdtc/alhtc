package com.alhtc.common.core.text;

import com.alhtc.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 类型转换器
 *
 * @author wangxiaoxu
 */
public class Convert {

	/**
	 * 转换为字符串<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
	 * 转换失败不会报错
	 *
	 * @param value 被转换的值
	 * @return 结果
	 */
	public static String toStr(Object value) {
		return toStr(value, null);
	}

	/**
	 * 转换为字符串<br>
	 * 如果给定的值为null，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 *
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static String toStr(Object value, String defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		if (value instanceof String) {
			return (String) value;
		}
		return value.toString();
	}

	/**
	 * 转换为int<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
	 * 转换失败不会报错
	 *
	 * @param value 被转换的值
	 * @return 结果
	 */
	public static Integer toInt(Object value) {
		return toInt(value, null);
	}

	/**
	 * 转换为int<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 *
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Integer toInt(Object value, Integer defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 转换为boolean<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
	 * 转换失败不会报错
	 *
	 * @param value 被转换的值
	 * @return 结果
	 */
	public static Boolean toBool(Object value) {
		return toBool(value, null);
	}

	/**
	 * 转换为boolean<br>
	 * String支持的值为：true、false、yes、ok、no，1,0 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 *
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Boolean toBool(Object value, Boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		valueStr = valueStr.trim().toLowerCase();
		switch (valueStr) {
			case "true":
				return true;
			case "false":
				return false;
			case "yes":
				return true;
			case "ok":
				return true;
			case "no":
				return false;
			case "1":
				return true;
			case "0":
				return false;
			default:
				return defaultValue;
		}
	}

	/**
	 * 将对象转为字符串<br>
	 * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
	 *
	 * @param obj 对象
	 * @return 字符串
	 */
	public static String utf8Str(Object obj) {
		return str(obj, CharsetKit.CHARSET_UTF_8);
	}

	/**
	 * 将对象转为字符串<br>
	 * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
	 *
	 * @param obj     对象
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String str(Object obj, Charset charset) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof byte[]) {
			return str(obj, charset);
		} else if (obj instanceof Byte[]) {
			byte[] bytes = ArrayUtils.toPrimitive((Byte[]) obj);
			return str(bytes, charset);
		} else if (obj instanceof ByteBuffer) {
			return str(obj, charset);
		}
		return obj.toString();
	}

	/**
	 * 转换为Long数组<br>
	 *
	 * @param str 被转换的值
	 * @return 结果
	 */
	public static Long[] toLongArray(String str) {
		return toLongArray(",", str);
	}

	/**
	 * 转换为Long数组<br>
	 *
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static Long[] toLongArray(String split, String str) {
		if (StringUtils.isEmpty(str)) {
			return new Long[]{};
		}
		String[] arr = str.split(split);
		final Long[] longs = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			final Long v = toLong(arr[i], null);
			longs[i] = v;
		}
		return longs;
	}

	/**
	 * 转换为long<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 *
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Long toLong(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Long) {
			return (Long) value;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			// 支持科学计数法
			return new BigDecimal(valueStr.trim()).longValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
