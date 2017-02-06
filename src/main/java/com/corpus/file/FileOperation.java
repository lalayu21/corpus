package com.corpus.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.corpus.entity.Feature;
import com.corpus.entity.Linux;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.Usage;
import com.corpus.ssh.RmtShellExecutor;
import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;

import ch.ethz.ssh2.SCPClient;

public class FileOperation {
	private List<PraatDetailSelect> trainortest;
	
	private Feature feature;

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	

	public List<PraatDetailSelect> getTrainortest() {
		return trainortest;
	}

	public void setTrainortest(List<PraatDetailSelect> trainortest) {
		this.trainortest = trainortest;
	}

	public FileOperation(List<PraatDetailSelect> trainortest){
		this.trainortest = trainortest;
	}
	
	public FileOperation(Feature feature){
		this.feature = feature;
	}
	
	//传入的是固定ip
	public long write(String fileName, String labelFile, Linux linux, Usage usage, String linuxPath, int labelType, int train, long time){

		File file = new File(fileName);
		boolean flag = true;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
				flag = false;
				System.out.println("新建文件失败");
				e.printStackTrace();
			}
		}
		System.out.println(file.getAbsolutePath());
		
		File filelabel = new File("scpLabel");
		if(!filelabel.exists()){
			filelabel.mkdir();
		}
		System.out.println(filelabel.getAbsolutePath());
		
		if(flag){
			try {
				FileWriter fw = new FileWriter(file);
				//BufferedWriter br = new BufferedWriter(fw);
				
				List<String> wavetag = new ArrayList<String>();
				

				File wavetagger = new File("wavetagger");
				if(!wavetagger.exists()){
					try {
						wavetagger.createNewFile();
					}catch(Exception e){
						e.printStackTrace();
					}
				}

				FileWriter fwtempwavetagger = new FileWriter(wavetagger.getAbsoluteFile());
				BufferedWriter brwavetagger = new BufferedWriter(fwtempwavetagger);
				
				RmtShellExecutor executor = new RmtShellExecutor(linux.getIp(), linux.getUsername(), linux.getPassword());
				RmtShellExecutor executorPraat = new RmtShellExecutor(usage.getIp(), usage.getLinuxUser(), usage.getLinuxPaw());
				
				String labeledResult = "";
				
				String label = "";
				String wave = "";
				
				
				if(train == 0){
					//time = System.currentTimeMillis();
					executor.exec("mkdir " + usage.getWavePath());
					executor.exec("mkdir " + usage.getWavePath() + "/label");
					executor.exec("mkdir " + usage.getWavePath() + "/wave");
					
					label = "/label/train";
					wave = "/wave/train";
				}else{
					label = "/label/test";
					wave = "/wave/test";
				}
				fw.write("#!/usr/bin/expect -f\n");
				
				for(int i = 0; i < trainortest.size(); i++){
					PraatDetailSelect item = trainortest.get(i);
					if(!wavetag.contains(item.getWave())){
						
						switch (labelType) {
						case 0:
							File filetemp = new File("scpLabel/" + item.getWave() + ".textgrid");
							if(!filetemp.exists()){
								try {
									System.out.println(filetemp.getAbsoluteFile());
									filetemp.createNewFile();
									FileWriter fwtemp = new FileWriter(filetemp);
									//BufferedWriter brtemp = new BufferedWriter(fwtemp);
									
									String contextlabel = "File type = \"ooTextFile\"\nObject class = \"TextGrid\"\nxmin = 0\nxmax = 242.2\ntiers? <exists>\nsize = 2\n ";
									
									fwtemp.write(contextlabel);
									
									contextlabel = "item []: \n    item [" + i + "]:\nclass = \"IntervalTier\" \nname = \"context\" \nxmin = 0 \nxmax = 242.2 \nintervals: size = 209\n";

									//brtemp.write(contextlabel);
									fwtemp.write(contextlabel);
									for(int j = 0; j < trainortest.size(); j++){
										PraatDetailSelect itemlabel = trainortest.get(j);
										if(item.getWave().equals(itemlabel.getWave())){
											contextlabel = "intervals [" + j + "]\nxmin=" + itemlabel.getStarttime() + "\nmax=" + itemlabel.getEndtime() + "\ntext=" + itemlabel.getResult();
											fwtemp.write(contextlabel);
										}
									}
									fwtemp.close();
									fileExist(usage.getWavePath() + label, executor);
									executorPraat.exec1(filetemp.getAbsolutePath(), filetemp.getName(), usage.getWavePath() + label, 1);
									//fw.write("scp " + linuxPath + "/" + item.getWave() + " " + usage.getLinuxUser() + "@" + usage.getIp() + ":/" + usage.getWavePath() + "/wave\n");
									String transContext = "scp " + linuxPath + "/" + item.getWave() + ".wav " + usage.getLinuxUser() + "@" + usage.getIp() + ":" + usage.getWavePath() + wave + "\n";
									freePassword(fw, transContext, usage);
									//br.write("scp " + item.getWave() + ".textgrid" + " " + usage.getLinuxUser() + "@" + linux.getIp() + ":/" + linuxPath + "/label");
									//br.write(usage.getLinuxPaw());
								} catch (Exception e) {
									// TODO: handle exception
									flag = false;
									System.out.println("新建文件失败");
									e.printStackTrace();
								}
							}
							break;
						case 1:
							if(!wavetagger.exists()){
								try {									
									String contextlabel = item.getWave() + "==" + item.getContext() + "=" + item.getOther() + "=" + item.getStarttime() + "=" + item.getEndtime();
									brwavetagger.write(contextlabel);
								} catch (Exception e) {
									// TODO: handle exception
									flag = false;
									System.out.println("新建文件失败");
									e.printStackTrace();
								}
							}

							String transContext = "scp " +  linuxPath + "/" + item.getWave() + ".wav " + usage.getLinuxUser() + "@" + usage.getIp() + ":" + usage.getWavePath() + wave + "\n";
							freePassword(fw, transContext, usage);
							break;
						case 2:
							/*File filefile = new File(item.getContext());
							if(!filefile.exists()){
								try {
									filefile.mkdir();
									
								}catch(Exception e){
									e.printStackTrace();
								}
							}*/
							
							if(!item.getContext().equals(labeledResult)){
								labeledResult = item.getContext();
								fileExist(usage.getWavePath() + "/" + labeledResult, executor);
							}
							String transContext1 = "scp " +  linuxPath + "/" + item.getWave() + ".wav " + usage.getLinuxUser() + "@" + usage.getIp() + ":" + usage.getWavePath() + "/" + labeledResult;
							freePassword(fw, transContext1, usage);
							break;
						default:
							break;
						}
						wavetag.add(item.getWave());
					}
				}
				
				fw.close();
				if(labelType == 1){
					fileExist(usage.getWavePath() + label, executor);
					//br.write("scp " + wavetagger.getAbsoluteFile() + "/wavetagger " + usage.getLinuxUser() + "@" + linux.getIp() + ":/" + linuxPath + "/label");
					executorPraat.exec1(wavetagger.getAbsolutePath(), wavetagger.getName(), usage.getWavePath() + label, 1);
				}else if (labelType == 0) {
					fileExist(usage.getWavePath() + wave, executor);
				}
				
				//批量复制
				executor.exec1(file.getAbsolutePath(), fileName, "Desktop/sh", 0);
				
				//br.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("文件写入失败");
				e.printStackTrace();
			}
		}
		return time;
	}
	
	//只是传入文件名
	public void write(String fileName, int featureType){
		File file = new File(fileName);
		boolean flag = true;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
				flag = false;
				System.out.println("新建文件失败");
				e.printStackTrace();
			}
		}
		
		if(flag){
			try {
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter br = new BufferedWriter(fw);
				String cmd = "";
				
				switch (featureType) {
				case 1://mfcc
					if(feature.getEnergy() == 0){
						br.write("--use-energy=true");
					}else{
						br.write("--use-energy=false");
					}
					
					br.write("--num-mel-bins=" + feature.getMelBins());
					br.write("--num-ceps=" + feature.getCeps());
					br.write("--low-freq=" + feature.getLowFreq());
					br.write("--high-freq=" + feature.getHighFreq());
					cmd = ". timit/steps/make_mfcc.sh";
					break;
				case 2://plp
					break;
				case 3://fbank
					break;
				default:
					break;
				}
				
				br.close();
				
				//上传文件并执行
				
				RmtShellExecutor exe = new RmtShellExecutor("127.0.0.1", "ynn", "123");
				//将配置文件上传至linux服务器
				exe.exec1(file.getAbsolutePath() + "/" + fileName, fileName, "feature/" + System.currentTimeMillis() + "/", 1);
				//设置文件属性为可执行文件
				//exe.exec("chmod +x feature/" + System.currentTimeMillis() + "/" + fileName);
				//执行提特征的程序
				exe.exec(cmd);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("文件写入失败");
				e.printStackTrace();
			}
		}
	}
	
	//生成公钥和私钥认证方式免密码
	public int freePassword(FileWriter fWriter, String context, Usage usage){
		int flag = 0;
		try{
			fWriter.write("spawn " + context + "\n");
			fWriter.write("set timeout 20\n");
			fWriter.write("expect \"" + usage.getLinuxUser() + "@" + usage.getIp() + "'s password\"\n");
			fWriter.write("exec sleep 2\n");
			fWriter.write("send \"" + usage.getLinuxPaw() + "\\r\"\n");
			fWriter.write("interact\n");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	
	//判断文件或者文件夹是否存在
	public void fileExist(String path, RmtShellExecutor exe){
		try {
			exe.exec("if [ -e " + path + " ];then echo \"exist\"; else mkdir " + path + ";fi");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//删除本地文件
	public boolean deleteFile(String path){
		boolean flag = false;
		File file = new File(path);
		if(file.exists() && file.isFile()){
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	//删除本地文件夹
	public boolean deleteFileDir(String path){
		File fileDir = new File(path);
		if(fileDir.exists() && fileDir.isDirectory()){
			File[] files = fileDir.listFiles();
			for(int i = 0; i < files.length; i++){
				if(files[i].isFile()){
					deleteFile(files[i].getAbsolutePath());
				}else{
					deleteFileDir(files[i].getAbsolutePath());
				}
			}
			
			if(fileDir.delete()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
}
