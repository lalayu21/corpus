package com.corpus.service.Impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import com.corpus.dao.CorpusDao;
import com.corpus.dao.FmtDao;
import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.corpus.service.CorpusService;
import com.corpus.service.FTPService;
import com.corpus.service.ReadContextService;
import com.corpus.thread.LeadinCorpusThread;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.text.normalizer.IntTrie;

@Service
public class CorpusServiceImpl implements CorpusService {
	
	@Resource
	CorpusDao corpusDao;
	
	@Resource
	FmtDao fmtDao;
	
	@Resource
	ReadContextService readContextService;
	
	@Resource
	FTPService ftpService;
	
	@Override
	public int create(JSONObject jsonObject) {
		
		Corpus corpus = new Corpus();
		CorpusFmt corpusFmt = new CorpusFmt();
		
		String name = jsonObject.getString("name");
		Integer type = jsonObject.getInt("type");
		String wavePath = jsonObject.getString("wavePath");
		String labelPath = jsonObject.getString("labelPath");
		
		int labelType = jsonObject.getInt("labelType");
		int context = jsonObject.getInt("context");
		int effective = jsonObject.getInt("effective");
		int gender = jsonObject.getInt("gender");
		int language = jsonObject.getInt("language");
		int speaker = jsonObject.getInt("speaker");
		String desp = jsonObject.getString("desp");
		
		int head = jsonObject.getInt("head");
		int code = jsonObject.getInt("code");
		int sample = jsonObject.getInt("sample");
		int bitersample = jsonObject.getInt("bitpersamples");
		int channel = jsonObject.getInt("channel");
		
		String waveip = jsonObject.getString("waveip");
		String waveUsername = jsonObject.getString("waveusername");
		String wavePassword = jsonObject.getString("wavepassword");
		
		String labelIp = jsonObject.getString("labelip");
		String labelUsername = jsonObject.getString("labelusername");
		String labelPassword = jsonObject.getString("labelpassword");
		
		//判断输入的信息是否正确		
		if(name == null || "".equals(name)){
			return 1;
		}else{
			if(checkName(name).equals("0")){
				corpus.setName(name);
				if(type == null || (type != 0 && type != 1)){
					return 1;
				}else{
					
					corpus.setType(type);
					
					corpus.setContext(context);
					corpus.setEffective(effective);
					corpus.setGender(gender);
					corpus.setLanguage(language);
					corpus.setSpeaker(speaker);
					
					corpus.setLabelType(labelType);
					corpus.setWavePath(wavePath);
					corpus.setLabelPath(labelPath);
					corpus.setDesp(desp);
					
					FtpConnect waveConnect = new FtpConnect();
					waveConnect.setIp(waveip);
					waveConnect.setPassword(wavePassword);
					waveConnect.setUsername(waveUsername);
					waveConnect.setPort(21);
					waveConnect.setPath(wavePath);
					
					FtpConnect labelConnect = new FtpConnect();
					labelConnect.setIp(labelIp);
					labelConnect.setPassword(labelPassword);
					labelConnect.setUsername(labelUsername);
					labelConnect.setPort(21);
					labelConnect.setPath(labelPath);
					
					FTPClient ftpClientLabel = new FTPClient();
					FTPClient ftpClientWave = new FTPClient();
											
					try {
						ftpClientWave = ftpService.getFtpClient(waveConnect);
						ftpClientLabel = ftpService.getFtpClient(labelConnect);
					} catch (Exception e) {
						// TODO: handle exception
						return 1;
					}
					
					if(corpus.getType() == 0){
						corpusDao.insertNo(corpus);
					}else{
						corpusDao.insertYes(corpus);
					}
					
					int id = corpusDao.selectIdByName(corpus.getName());
					
					corpusFmt.setHead(head);
					corpusFmt.setCode(code);
					corpusFmt.setSample(sample);
					corpusFmt.setBitpersamples(bitersample);
					corpusFmt.setChannel(channel);
					corpusFmt.setCorpus(id);
					
					fmtDao.insertFmtByCorpus(corpusFmt);
					
					corpus.setId(id);
					corpus.setIp(waveip);
					corpus.setUsername(waveUsername);
					corpus.setPassword(wavePassword);
					corpus.setFileType(0);
					corpusDao.insert2ip(corpus);
					
					corpus.setIp(labelIp);
					corpus.setId(id);
					corpus.setUsername(labelUsername);
					corpus.setPassword(labelPassword);
					corpus.setFileType(1);
					corpusDao.insert2ip(corpus);
					
					if(corpusDao.selectNameByName(name) == null){
						return 1;
					}else{
						
						/*Linux linux = new Linux();
						linux.setIp("127.0.0.2");
						linux.setUsername("ynn");
						linux.setPassword("123");
						linux.setPort("21");
						linux.setPath(".");
						
						Thread thread = new LeadinThread(1, 0, linux);
						
						thread.start();*/
						
						//wavetagger
						/*readContextService.getLabel("ynn", "123", "127.0.0.1", "21", ".");*/


						LeadinCorpusThread leadinCorpusThread = new LeadinCorpusThread(ftpClientWave, ftpClientLabel, labelType, id, labelConnect, waveConnect, corpusFmt, readContextService);
						leadinCorpusThread.start();
						
						
						/*readContextService.getListAccess("ynn", "123", "127.0.0.1", "21", ".");*/
						
					}
				}
			}
		}
		return 0;
	}
	
	/*@Override*/
	/*public String createPraat(JSONArray corpusJSON) {
		Corpus corpus = new Corpus();
		String name = corpusJSON.getJSONObject(0).getString("value");
		if(name == null || "".equals(name)){
			return "1";
		}else{
			if(checkName(name) == "1"){
				return "2";
			}else{
				corpus.setName(name);
				Integer type = corpusJSON.getJSONObject(1).getInt("value");
				if(type == null || (type != 0 && type != 1)){
					return "语料库的类型参数不符合要求";
				}else{
					String wavePath = corpusJSON.getJSONObject(4).getString("value");
					String labelPath = corpusJSON.getJSONObject(5).getString("value");
					String labelType = corpusJSON.getJSONObject(3).getString("value");
					
					corpus.setType(type);
					corpus.setLabelResult(corpusJSON.getJSONObject(2).getString("value"));
					corpus.setLabelType(corpusJSON.getJSONObject(3).getInt("value"));
					corpus.setWavePath(corpusJSON.getJSONObject(4).getString("value"));
					corpus.setLabelPath(corpusJSON.getJSONObject(5).getString("value"));
					corpus.setRatio(corpusJSON.getJSONObject(6).getInt("value"));
					corpus.setDesp(corpusJSON.getJSONObject(7).getString("value"));
					
					corpusDao.insertYes(corpus);
					int id = corpusDao.selectIdByName(corpus.getName());
					
					if(corpusDao.selectNameByName(name) == null){
						return "3";
					}else{
						
						FTPClient ftpClientWave = ftpService.getFtpClient("ynn", "123", "127.0.0.1", 21, wavePath);
						FTPClient ftpClientLabel = ftpService.getFtpClient("ynn", "123", "127.0.0.1", 21, labelPath);
						
						readContextService.getLabel(ftpClientWave, ftpClientLabel, labelType, id);
						
						return "0";
					}
				}
			}
		}
	}*/

	@Override
	public String checkName(String name) {
		String nameSelect = corpusDao.selectNameByName(name);
		if(nameSelect == null){
			return "0";
		}else{
			return "1";
		}
	}

	@Override
	public List<Corpus> selectAll() {
		return corpusDao.selectAll();
	}

	@Override
	/* 
	 * 返回值：corpus中不包含id
	*/
	public Corpus selectAllById(int id) {
		return corpusDao.selectAllById(id);
		
	}

	@Override
	public List<Corpus> selectByType(int flag) {
		// TODO Auto-generated method stub
		return corpusDao.selectCorpusByType(flag);
	}

	@Override
	public List<Corpus> selectByLabelType(int flag) {
		// TODO Auto-generated method stub
		List<Corpus> corpus = new ArrayList<Corpus>();
		switch (flag) {
		case 2:
			corpus = corpusDao.selectCorpusByLabelType("context = 1");
			break;
		case 3:
			corpus = corpusDao.selectCorpusByLabelType("gender = 1");
			break;
		case 4:
			corpus = corpusDao.selectCorpusByLabelType("speaker = 1");
			break;
		case 5:
			corpus = corpusDao.selectCorpusByLabelType("language = 1");
			break;
		case 6:
			corpus = corpusDao.selectCorpusByLabelType("effective = 1");
			break;
		default:
			corpus = corpusDao.selectCorpusByType(flag);
			break;
		}
		return corpus;
	}

	@Override
	public List<Corpus> selectByInput(int type, String input) {
		// TODO Auto-generated method stub
		List<Corpus> corpus = new ArrayList<Corpus>();
		input = "\"%" + input + "%\"";
		String parameter = "";
		switch (type) {
		case 1:
			parameter = "select * from corpus where type = 1 and name like " + input + " union select * from corpus where type = 1 and desp like " + input;
			break;
		case 2:
			parameter = "select * from corpus where context = 1 and name like " + input + " union select * from corpus where context = 1 and desp like " + input;
			break;
		case 3:
			parameter = "select * from corpus where gender = 1 and name like " + input + " union select * from corpus where gender = 1 and desp like " + input;
			break;
		case 4:
			parameter = "select * from corpus where speaker = 1 and name like " + input + " union select * from corpus where speaker = 1 and desp like " + input;
			break;
		case 5:
			parameter = "select * from corpus where language = 1 and name like " + input + " union select * from corpus where language = 1 and desp like " + input;
			break;
		case 6:
			parameter = "select * from corpus where effective = 1 and name like " + input + " union select * from corpus where effective = 1 and desp like " + input;
			break;
		default:
			break;
		}
		corpus = corpusDao.selectCorpusByInput(parameter);
		return corpus;
	}

	@Override
	public String createPraat(JSONArray corpus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCorpusFlag(JSONObject jsonObject, int flag) {
		// TODO Auto-generated method stub
		Corpus corpus = new Corpus();
		int id = corpusDao.selectIdByName(jsonObject.getString("name"));
		corpus.setId(id);
		corpus.setFlag(flag);
		corpusDao.updateCorpusFlag(corpus);
	}

	@Override
	public JSONObject leadCorpus(JSONObject jsonObject) {
		return null;
	}
}
