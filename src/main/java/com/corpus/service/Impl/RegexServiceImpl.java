package com.corpus.service.Impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.corpus.service.RegexService;

@Service
public class RegexServiceImpl implements RegexService {

	@Override
	public String sizeRegex(String text) {
		String pattern = "size=(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}

	@Override
	public String nameRegex(String text) {
		String pattern = "name=\"(.*)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}

	@Override
	public String minRegex(String text) {
		String pattern = "min=(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}
	
	@Override
	public String maxRegex(String text) {
		String pattern = "max=(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
		if(m.find())
			return m.group(1);
		else
			return null;
	}

	@Override
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
