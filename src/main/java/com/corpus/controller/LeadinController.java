package com.corpus.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.corpus.service.CorpusService;
import com.corpus.thread.LeadinCorpusThread;
import com.millery.utils.DataSourceContextHolder;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/data")
public class LeadinController {
	
	@Resource
	CorpusService corpusLeadinService;
	
	
	/* 函数名：leadinAction
	 * 参数：无
	 * 返回值：
	 * 功能，从其他页面跳转至创建新语料库页面
	 * */
	@RequestMapping("/in")
	public String leadinAction(ModelMap map){
		DataSourceContextHolder.setDbType("dataSource");
		return "/corpus/lead";
	}
	
	@RequestMapping("/checkName")
	public void checkName(@RequestParam("name") String name, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		String flag = corpusLeadinService.checkName(name);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(flag);
		response.getWriter().flush();
	}

	
	
	/* 函数名：leadin
	 * 参数：corpus（string）
	 * 返回值：0：代表开始导入，1：代表输入的参数有问题，2：代表导入失败
	 * 功能，根据用户设定的语料库信息创建新的语料库
	 * */
	@RequestMapping(value = "/begin", method = RequestMethod.POST)
	public void leadin(@RequestParam("corpus") String information, HttpServletResponse response) throws IOException{
		int flag = 0;

		DataSourceContextHolder.setDbType("dataSource");
		if(information == null || "".equals(information)){
			flag = 1;
		}else{
			String corpus_string = new String(information.getBytes("ISO-8859-1"),"UTF-8");
			
			JSONObject corpus = JSONObject.fromObject(corpus_string);
			System.out.println(corpus_string);
			
			//JSONArray corpus = JSONArray.fromObject(corpus_string);
			flag = corpusLeadinService.create(corpus);
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(flag);
		response.getWriter().flush();
	}
}
