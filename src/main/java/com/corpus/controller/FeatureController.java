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
import com.corpus.service.FeatureService;
import com.millery.utils.DataSourceContextHolder;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/feature")
public class FeatureController {
	
	@Resource
	CorpusService corpusService;
	
	@Resource
	FeatureService featureService;
	
	@RequestMapping("/action")
	public String action(ModelMap map, @RequestParam("id") String id){
		DataSourceContextHolder.setDbType("dataSource");
		if(id == null){
			System.out.println("id不正确");
			return null;
		}else{
			int corpus = Integer.parseInt(id);
			map.put("corpus", corpusService.selectAllById(corpus));
			map.put("id", id);
			return "/corpus/operate/feature";
		}
	}
	
	
	/* 功能：获取前台传递的mfcc参数，将参数写在config文件中，
	 * 	将config文件发送到linux服务器上，运行服务器上的run.sh
	 * 	将mfcc特征写到指定位置
	 *	检测提特征的程序是否正常启动，
	 * 返回值：启动返回0，启动失败返回1*/
	@RequestMapping(value = "/getFeature", method = RequestMethod.POST)
	public void getMfcc(@RequestParam String jsonArray, HttpServletResponse response) throws IOException{
		
		JSONObject jsonObject = new JSONObject();

		DataSourceContextHolder.setDbType("dataSource");
		if(jsonArray == null){
			jsonObject.put("error", "输入信息有误");
		}else{
			String paramUTF8 = new String(jsonArray.getBytes("ISO-8859-1"), "utf-8");
			
			JSONObject information = JSONObject.fromObject(paramUTF8);
			
			featureService.insert2feature(information);
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
}
