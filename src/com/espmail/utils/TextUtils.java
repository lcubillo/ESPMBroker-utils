package com.espmail.utils;

import java.sql.Date;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TextUtils {

	private static final SimpleDateFormat FORMAT_DATE
			= new SimpleDateFormat("dd/MM/yyyy");
	
	private static final SimpleDateFormat FORMAT_TIMESTAMP
			= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
    
    public static Integer asInteger(String value) {
    	return isEmpty(value)? null : new Integer(value); 
    }
    
    public static Float asFloat(String value) {
    	return isEmpty(value)? null : new Float(value); 
    }
    
    public static Date asDate(String value) {
    	try {
			return isEmpty(value)? null : new Date(FORMAT_DATE.parse(value).getTime());
		} catch (ParseException e) {
			return null;
		} 
    }
    
    public static Timestamp asTimestamp(String value) {
    	try {
			return isEmpty(value)? null : new Timestamp(FORMAT_TIMESTAMP.parse(value).getTime());
		} catch (ParseException e) {
			return null;
		} 
    }
    
    public static String asString(Number number) {
    	return number == null? null : number.toString();
    }
    
    public static String asString(Date date) {
    	return date == null? null : FORMAT_DATE.format(date);
    }
    
    public static String asString(Timestamp date) {
    	return date == null? null : FORMAT_TIMESTAMP.format(date);
    }
    
    public static String asString(String value) {
    	return isEmpty(value)? null : value;
    }
    
    public static String[] split(String value) {
    	if (value != null) {
    		if (value.startsWith(",")) {
    			value = value.substring(1);
    		}
    		
    		if (value.endsWith(",")) {
    			value = value.substring(0, value.length() - 1);
    		}

    		return value.split(",\\s*");
    	}

    	return null;
    }

    public static  String asStringDB(String value){
        if (null == value ||
                value.length()==0)
            return "NULL";
        value.replace('\'','\"');

        return null;
    }
}