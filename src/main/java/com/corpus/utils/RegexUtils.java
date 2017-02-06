package com.corpus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	public String sizeRegex(String text) {
		String pattern = "size=(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}

	public String nameRegex(String text) {
		String pattern = "name=\"(.*)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}

	public String minRegex(String text) {
		String pattern = "min=(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}
	
	public String maxRegex(String text) {
		String pattern = "max=(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}

	public String textRegex(String text) {
		String pattern = "\"(.*)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}
}
