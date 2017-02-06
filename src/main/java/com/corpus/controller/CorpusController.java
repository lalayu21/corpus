package com.corpus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.corpus.entity.Corpus;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.service.CorpusService;
import com.corpus.wave.service.FileService;
import com.millery.utils.DataSourceContextHolder;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/data")
public class CorpusController {
	
	
	@Resource
	CorpusService corpusService;
	
	@Resource
	CorpusService corpusLeadinService;
	
	@Resource
	FileService fileService;
	
	
	@RequestMapping("/detail")
	public String corpusDetail(ModelMap map, @RequestParam("id") String idString, @RequestParam String type){
		
		if(idString == null){
			System.out.println("传入的语料库的id值不存在");
		}else{
			int id = Integer.parseInt(idString);

			DataSourceContextHolder.setDbType("dataSource");
			
			Corpus corpus = corpusService.selectAllById(id);
			if(corpus.getLabelType() == 2){
				//获取该语料库的所有标注结果	
				List<PraatDetailSelect> result = new ArrayList<PraatDetailSelect>();
				result = fileService.getLabelResult(id);
				map.put("fileResult", result);
			}
			map.put("corpus", corpus);
			map.put("id", id);
			map.put("type", type);
		}
		return "/corpus/corpusDetail";
	}

	/* 函数名：corpusListAction
	 * 参数：无
	 * 返回值：
	 * 功能，从其他页面跳转至创建新语料库页面
	 * */
	@RequestMapping("/corpusList")
	public String corpusListAction(ModelMap map){
		DataSourceContextHolder.setDbType("dataSource");
		List<Corpus> corpus = corpusLeadinService.selectAll();
		map.put("corpus", corpus);
		return "/corpus/query";
	}
	
	@RequestMapping(value = "/corpusListType", method = RequestMethod.GET)
	public void getCorpusListType(@RequestParam String flag, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("corpus", corpusLeadinService.selectByType(Integer.parseInt(flag)));
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/corpusListLabelType", method = RequestMethod.GET)
	public void getCorpusListLabelType(@RequestParam String flag, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("corpus", corpusLeadinService.selectByLabelType(Integer.parseInt(flag)));
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	//获取用户条件筛选的语料库
	@RequestMapping(value = "/corpusListByInput", method = RequestMethod.POST)
	public void getCorpusListByInput(@RequestParam String type, @RequestParam String input, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(input == null){
			jsonObject.put("error", "输入的内容不正确");
		}else{
			String temp = new String(input.getBytes("ISO-8859-1"), "UTF-8");
			jsonObject.put("corpus", corpusService.selectByInput(Integer.parseInt(type), temp));
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
}

