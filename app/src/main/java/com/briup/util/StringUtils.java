package com.briup.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    //Determine if the string starts with a number
    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
