package com.test.util;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 专治八阿哥的孟老师
 */
public class ParameterUtil {

    public static Date getDate(HttpServletRequest request, String name, String pattern) {
        String param = request.getParameter(name);
        if (param == null || param.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(param);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer getInteger(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        if (param == null || param.trim().length() == 0) {
            return null;
        }
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Double getDouble(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        if (param == null || param.trim().length() == 0) {
            return null;
        }
        try {
            return Double.parseDouble(param);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getString(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        if (param == null || param.trim().length() == 0) {
            return null;
        }
        return param.trim();
    }

    public static boolean isBlank(String param) {
        if (param == null || param.trim().length() == 0) {
            return true;
        }
        return false;
    }

}
