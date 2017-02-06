package com.corpus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.corpus.entity.AttributeValues;
import com.corpus.entity.Attributes;
import com.corpus.entity.AttributesAndValue;
import com.corpus.entity.Corpus;
import com.corpus.entity.RegTask;
import com.corpus.service.CorpusService;
import com.corpus.service.LabelService;
import com.corpus.wave.service.PraatService;
import com.millery.utils.DataSourceContextHolder;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/label")
public class LabelController {
	
	@Resource
	PraatService praatService;
	
	@Resource
	CorpusService corpusService;
	
	@Resource
	LabelService labelService;
	
	//跳转页面
	@RequestMapping("/action")
	public String action(ModelMap map, @RequestParam("id") String idString){
		JSONObject jsonObject = new JSONObject();
		
		DataSourceContextHolder.setDbType("dataSource");
		try {
			int id = Integer.parseInt(idString);
			Corpus corpus = new Corpus();
			corpus = corpusService.selectAllById(id);
			corpus.setId(id);
			map.put("corpus", corpus);
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("error", "输入的信息有误");
		}
		return "/corpus/operate/label";
	}
	
	//获取所有属性
	@RequestMapping(value="/attrname")
	public void queryAllattrname(HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		
		List<AttributesAndValue> attributesAndValues = new ArrayList<AttributesAndValue>();
		
		DataSourceContextHolder.setDbType("dataSourceLabel");
		attributesAndValues = labelService.queryAllAttributes();
		
		jsonObject.put("attributesAndValues", attributesAndValues);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//根据属性id获取属性值
	@RequestMapping(value = "/queryAllValuesname")
	public void queryAllValuesname(HttpServletResponse response, @RequestParam String id){
		JSONObject jsonObject = new JSONObject();
		int valueID = 0;
		
		if(id == null || "".equals(id)){
			jsonObject.put("error", "输入信id为空");
		}else{
			try {
				valueID = Integer.parseInt(id);
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("error", "输入的信息不是int值");
			}
			DataSourceContextHolder.setDbType("dataSourceLabel");
			List<AttributeValues> attrValueslist = labelService.queryAllAttributeValues(valueID);
			jsonObject.put("attrvalues", attrValueslist);
		}
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//获取所有的标注属性
	@RequestMapping(value = "/querylabelattr")
	public void querylabelattr(HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		
		try {
			DataSourceContextHolder.setDbType("dataSourceLabel");
			List<Attributes> attributes = labelService.querylabelattr();
			jsonObject.put("attributes", attributes);
			
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//判断人物名称是否存在
	@RequestMapping(value = "/checkTaskname")
	public void checkTaskname(@RequestParam String task_name, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		if(task_name == null || "".equals(task_name)){
			jsonObject.put("error", "输入的信息有误");
		}else{
			try{
				String inputer = new String( task_name.getBytes("ISO-8859-1") , "UTF-8");
				DataSourceContextHolder.setDbType("dataSourceLabel");
				int tasks = labelService.checkTaskname(inputer);
				if(tasks==0){	
					 response.setCharacterEncoding("UTF-8");
					 response.getWriter().write("1");
					 response.flushBuffer();
				}else{
					 response.setCharacterEncoding("UTF-8");
					 response.getWriter().write("0");
					 response.flushBuffer();
					 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/addTasks", method = RequestMethod.POST)
	public String addTask(ModelMap map, @ModelAttribute("pojo") RegTask regTask){
		DataSourceContextHolder.setDbType("dataSourceLabel");
		
		int flag = labelService.insertNewTask(regTask);
		
		if(flag == 0){
			map.put("error", "新建任务失败");
		}else{
			map.put("success", "任务已成功建立");
		}
		
		try {
			int id = Integer.parseInt(regTask.getId());
			Corpus corpus = new Corpus();
			corpus = corpusService.selectAllById(id);
			corpus.setId(id);
			map.put("corpus", corpus);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("corpusInfo", "请刷新页面重试");
		}
		return "/corpus/operate/label";
	}
	
}
