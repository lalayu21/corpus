package com.corpus.service;

import java.util.List;

import com.corpus.entity.Corpus;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface CorpusService {
	
	/*判断语料库名称是否存在*/
	public String checkName(String name);
	
	//判断用户输入的信息是否正确
	public JSONObject leadCorpus(JSONObject jsonObject);
	
	/*创建语料库*/
	public int create(JSONObject jsonObject);
	public String createPraat(JSONArray corpus);
	
	/*查询所有数据*/
	public List<Corpus> selectAll();
	public List<Corpus> selectByType(int flag);
	public List<Corpus> selectByLabelType(int flag);
	public List<Corpus> selectByInput(int type, String input);
	
	
	/*根据id查询语料库数据*/
	public Corpus selectAllById(int id);
	
	
	//更新语料库的状态，创建完成或者未完成
	public void updateCorpusFlag(JSONObject corpus, int flag);
}
