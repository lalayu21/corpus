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

import com.corpus.dao.CorpusDao;
import com.corpus.entity.TrainSet;
import com.corpus.entity.Usage;
import com.corpus.service.LabelService;
import com.corpus.thread.NewTrainingSet;
import com.millery.utils.DataSourceContextHolder;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/training")
public class TrainingController {
	
	@Resource
	LabelService labelService;
	
	@Resource
	CorpusDao corpusDao;
	
	//action
	@RequestMapping("/action")
	public String action(ModelMap map, @RequestParam("id") String idString, @RequestParam String type, @RequestParam String labelType){
		DataSourceContextHolder.setDbType("dataSource");
		
		if(idString == null){
			map.put("error", "请选择正确的语料库");
		}else{
			map.put("name", corpusDao.selectNameById(Integer.parseInt(idString)));
			map.put("id", idString);
			map.put("labelType", labelType);
			map.put("type", type);
		}
		return "/corpus/operate/getSet";
	}
	
	//开始获取训练集和测试集
	@RequestMapping(value = "/getSet", method = RequestMethod.GET)
	public void training(@RequestParam String jsonArray, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		
		String information = new String(jsonArray.getBytes("ISO-8859-1"), "UTF-8");
		JSONObject setInf = JSONObject.fromObject(information);
		
		//这里获取异常，返回错误信息
		int id = 0;
		int type = 0;
		String train = "";
		String test = "";
		String per = "";
		String labelType = "";
		String name = "";
		try {
			id = setInf.getInt("id");
			type = setInf.getInt("type");
			train = setInf.getString("train");
			test = setInf.getString("test");
			per = setInf.getString("per");
			labelType = setInf.getString("labelType");
			name = setInf.getString("name");
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("error", "输入的信息有误");
		}
		
		//判断条件还有equals（“”）
		if(labelType == null || "".equals(labelType)){
			jsonObject.put("error", "输入的信息有误");
		}else{
			boolean flag = false;
			//查询语料库拥有多少条数据，计算测试集和训练集的个数
			if(per == null || "".equals(per)){
				if(train == null || test == null || "".equals(train) || "".equals(test)){
					jsonObject.put("error", "输入的信息有误");
				}else{
					flag = true;
					NewTrainingSet newTrainingSet = new NewTrainingSet(id, type, Float.parseFloat(train), Float.parseFloat(test), labelType, name, labelService);
					newTrainingSet.start();
					//Map<String, List<WaveList>> wave = new HashMap<String, List<WaveList>>();
					//wave = labelService.getTTSetByTime(id, type, Float.parseFloat(train), Float.parseFloat(test), labelType, name);
					/*jsonObject.put("list", labelService.getTTSetByTime(id, type, Float.parseFloat(train), Float.parseFloat(test), labelType, name));*/
				}
			}else{
				
				flag = true;
				NewTrainingSet newTrainingSet = new NewTrainingSet(id, type,per, labelType, name, labelService);
				newTrainingSet.start();
				//labelService.getTTSet(id, type,per, labelType, name);
				//Map<String, List<WaveList>> wave = new HashMap<String, List<WaveList>>();
				//wave = labelService.getTTSet(id, type,per, labelType, name);
				/*jsonObject.put("list", labelService.getTTSet(id, type,per, labelType, name));*/
			}
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	
	/* 跳转至训练集列表界面
	 * list
	 * 参数：corpusID
	 * 返回值：getSetList.jsp
	 * */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getSetList(ModelMap map, @RequestParam("id") String corpusID, @RequestParam String type, @RequestParam String labelType){
		DataSourceContextHolder.setDbType("dataSource");
		int corpus = 0;
		if(corpusID == null || "".equals(corpusID)){
			map.put("error", "请求数据有错");
		}else{
			try {
				corpus = Integer.parseInt(corpusID);
				
				//获取set list
				List<TrainSet> trainSet = new ArrayList<TrainSet>();
				trainSet = labelService.getSetListService(corpus);
				map.put("type", type);
				map.put("labelType", labelType);
				map.put("corpus", corpusID);
				map.put("name", corpusDao.selectNameById(corpus));
				map.put("list", trainSet);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("error", "请求数据有错");
			}
		}
		return "/corpus/operate/getSetList";
	}
	
	/* 获取id为确定值的训练集使用情况
	 * usage
	 * param：id
	 * return：list<Usage>*/
	@RequestMapping(value = "/usage", method = RequestMethod.GET)
	public void getUsage(String id, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		int setID = 0;
		JSONObject jsonObject = new JSONObject();
		if(id == null || "".equals(id)){
			jsonObject.put("error", "输入信息有误");
		}else{
			try {
				setID = Integer.parseInt(id);
				List<Usage> usage = new ArrayList<Usage>();
				usage = labelService.getUsage(setID);
				jsonObject.put("list", usage);
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("error", "输入信息有误");
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	//给训练集id增加新的使用者信息
	@RequestMapping(value = "/setUsage", method = RequestMethod.GET)
	public void setUsage(String jsonArray, HttpServletResponse response){
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		
		if(jsonArray == null || "".equals(jsonArray)){
			jsonObject.put("error", "输入信息有误");
		}else{
			try {
				String temp = new String(jsonArray.getBytes("ISO-8859-1"), "UTF-8");
				
				JSONObject information = JSONObject.fromObject(temp);
				
				int id = information.getInt("setID");
				
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2 = labelService.setUsage(information);
				if(!"".equals(jsonObject2.getString("error"))){
					System.out.println("error");
					jsonObject.put("error", jsonObject2.getString("error"));
				}else{
					System.out.println("success");
				}
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("error", "输入的信息有误");
			}
		}
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("返回信息出错");
		}
	}
	
	/* 根据id获取使用情况
	 * */
	@RequestMapping(value = "/getUsageById", method = RequestMethod.GET)
	public void getUsageById(@RequestParam String id, HttpServletResponse response){
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(id == null || "".equals(id)){
			jsonObject.put("error", "输入信息有误");
		}else{
			try {
				Usage usage = new Usage();
				usage = labelService.getUsageById(Integer.parseInt(id));
				jsonObject.put("obj", usage);
				
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(jsonObject.toString());
				response.getWriter().flush();
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("error", "输入信息有误");
			}
			
		}
	}
	
	/* 获取某一标注类型的所有标注结果
	 * 性别1，语种2，说话人3，有效语音4
	 * */
	@RequestMapping(value = "/getGenderSet", method = RequestMethod.POST)
	public void getGenderSet(@RequestParam String flag, HttpServletResponse response){
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		try {
			int labelType = Integer.parseInt(flag);
			//获取的标注结果的值，仅限于文本输入的结果，该方法不适用于单选框输入的标注结果
			String result = "";
			jsonObject.put("data", result);
			
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
