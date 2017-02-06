package com.corpus.controller;

import java.awt.Paint;
import java.awt.print.Pageable;
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
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.WaveList;
import com.corpus.service.CorpusService;
import com.corpus.wave.service.FileService;
import com.corpus.wave.service.PraatService;
import com.corpus.wave.service.WaveService;
import com.corpus.wave.service.Wavetagger;
import com.millery.utils.DataSourceContextHolder;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/data")
public class WaveController {

	@Resource
	Wavetagger wavetagger;
	
	@Resource
	WaveService waveService;
	
	@Resource
	PraatService praatService;
	
	@Resource
	CorpusService corpusService;
	
	@Resource
	FileService fileService;
	
	@RequestMapping("/waves")
	public String waveList(ModelMap map, @RequestParam("id") String idString, @RequestParam String type, @RequestParam String labelType, @RequestParam("name") String name){

		DataSourceContextHolder.setDbType("dataSource");
		if(idString == null){
			System.out.println("输入的语料库id不存在");
		}else{
			
			try {
				int id = Integer.parseInt(idString);
				map.put("name", name);
				map.put("corpus", id);
				map.put("type", type);
				map.put("labelType", labelType);
				map.put("path", corpusService.selectAllById(id).getWavePath());
				
				Corpus corpus = corpusService.selectAllById(id);
				map.put("context", corpus.getContext());
				map.put("gender", corpus.getGender());
				map.put("speaker", corpus.getSpeaker());
				map.put("language", corpus.getLanguage());
				map.put("effective", corpus.getEffective());
				
				if("2".equals(labelType)){
					List<PraatDetailSelect> result = fileService.getLabelResult(id);
					map.put("fileResult", result);
				}
				
				List<WaveList> waveLists = new ArrayList<WaveList>();
				
				map.put("number", waveService.getNumberInCorpus(id, labelType));
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("输入的内容不正确");
			}
		}
		return "/corpus/waveList";
	}
	
	//获取音频列表
	@RequestMapping(value = "/getWaveList", method = RequestMethod.GET)
	public void getWaveList(@RequestParam String start, @RequestParam String end, @RequestParam String id, @RequestParam String labelType, @RequestParam String type, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		if(id == null || "".equals(id) || labelType == null || "".equals(labelType)){
			jsonObject.put("error", "信息错误");
		}else{
			try {
				int idint = Integer.parseInt(id);
				
				SelectWavelistPage selectWavelistPage = new SelectWavelistPage();
				selectWavelistPage.setCorpus(idint);
				selectWavelistPage.setStart(Integer.parseInt(start));
				selectWavelistPage.setEnd(Integer.parseInt(end));
				selectWavelistPage.setType(Integer.parseInt(type));
				List<WaveList> waveLists = new ArrayList<WaveList>();
				
				if("0".equals(labelType)){
					//praat
					waveLists = praatService.getWaveList(selectWavelistPage);
				}else if ("1".equals(labelType)) {
					//wavetagger
					waveLists = wavetagger.getName(selectWavelistPage);
				}else{
					//file
					waveLists = fileService.getWaveList(selectWavelistPage);
				}
				jsonObject.put("wave", waveLists);
				
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("error", "信息错误");
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
	
	@RequestMapping(value = "/waveDetail", method = RequestMethod.GET)
	public void waveDetail(@RequestParam("id") String idString, @RequestParam String type, @RequestParam String labelType, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(labelType == null || idString == null || type == null){
			jsonObject.put("message", "音频文件信息不正确");
		}else{
			jsonObject = waveService.getDetail(idString, labelType);
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/wavePraatDetail", method = RequestMethod.GET)
	public void wavePraatDetail(@RequestParam("id") String idString, @RequestParam String type, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(type == null || idString == null){
			jsonObject.put("message", "音频文件信息不正确");
		}else{
			jsonObject = waveService.getPraatDetail(idString, type);
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/waveFileDetail", method = RequestMethod.GET)
	public void waveFileDetail(@RequestParam("id") String idString, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(idString == null || "".equals(idString)){
			jsonObject.put("message", "音频文件信息不正确");
		}else{
			try {
				jsonObject = waveService.getFileDetail(Integer.parseInt(idString));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	//获取praat标注时的一条语音中所有片段的id
	@RequestMapping(value = "/getIdList", method = RequestMethod.GET)
	public void getIdlistInPraat(@RequestParam("id") String waveList, @RequestParam String type, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(waveList == null){
			jsonObject.put("error", "wave为空");
		}else{
			//获取数据
			List<Integer> idList = praatService.getIdByWaveList(Integer.parseInt(waveList), Integer.parseInt(type)-2);
			if(idList == null){
				jsonObject.put("error", "没有找到相应的wave，请确认wave名称是否正确");
			}else{
				jsonObject.put("idList", idList);
			}
			
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/changeResultPraat", method = RequestMethod.GET)
	public void changeLabeledPraat(@RequestParam String id, @RequestParam String result, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(id == null || result == null){
			jsonObject.put("error", "输入的信息有误");
		}else{
			String context = new String(result.getBytes("ISO-8859-1"),"UTF-8");
			praatService.changeLabeledPraatService(Integer.parseInt(id), context);
			jsonObject.put("massage", "标注结果已修改");
		}

		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/changeResultWavetagger", method = RequestMethod.GET)
	public void changeLabeledWavetagger(@RequestParam String id, @RequestParam String result, @RequestParam String other, HttpServletResponse response) throws IOException{
		DataSourceContextHolder.setDbType("dataSource");
		JSONObject jsonObject = new JSONObject();
		if(id == null || result == null){
			jsonObject.put("error", "输入的信息有误");
		}else{
			String context = new String(result.getBytes("ISO-8859-1"),"UTF-8");
			String other1 = new String(other.getBytes("ISO-8859-1"),"UTF-8");
			wavetagger.changeLabeledWavetaggerService(Integer.parseInt(id), context, other1);
			jsonObject.put("massage", "标注结果已修改");
		}

		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
	}
	
	//删除不要的音频
	@RequestMapping(value = "/delete")
	public void deleteWave(@RequestParam String id, @RequestParam String labelType, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		if(id == null || labelType == null || "".equals(id) || "".equals(labelType)){
			jsonObject.put("error", "输入信息有误");
		}else{
			try {
				if (waveService.deleteWave(Integer.parseInt(id), labelType).getString("error") != null) {
					jsonObject.put("error", "操作失败");
				}else{
					jsonObject.put("error", "");
				}
			} catch (Exception e) {
				jsonObject.put("error", "");
				// TODO: handle exception
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
}
