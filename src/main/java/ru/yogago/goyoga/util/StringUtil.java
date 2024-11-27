package ru.yogago.goyoga.util;


import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static Boolean isBlank(String string) {
		return StringUtils.isBlank(string);
	}


	public static String cutSubstring(String string, String subString) {
		String source, delete;
		source = string;
		delete = subString;
		source = source.replace (delete, "");
		return source;
	}
}
