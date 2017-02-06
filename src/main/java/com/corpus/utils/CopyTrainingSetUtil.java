package com.corpus.utils;

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

public class CopyTrainingSetUtil {
	
	private List<PraatDetailSelect> trainortest;

	public List<PraatDetailSelect> getTrainortest() {
		return trainortest;
	}

	public void setTrainortest(List<PraatDetailSelect> trainortest) {
		this.trainortest = trainortest;
	}

	public CopyTrainingSetUtil(List<PraatDetailSelect> trainortest){
		this.trainortest = trainortest;
	}
	
	public void write(String fileName, String labelFile, Linux linux, Usage usage, String linuxPath, int labelType, int train){
		
		FileOperationUtil fileOperationUtil = new FileOperationUtil();

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
									fileOperationUtil.fileExist(usage.getWavePath() + label, executor);
									executorPraat.exec1(filetemp.getAbsolutePath(), filetemp.getName(), usage.getWavePath() + label, 1);
									//fw.write("scp " + linuxPath + "/" + item.getWave() + " " + usage.getLinuxUser() + "@" + usage.getIp() + ":/" + usage.getWavePath() + "/wave\n");
									String transContext = "scp " + linuxPath + "/" + item.getWave() + ".wav " + usage.getLinuxUser() + "@" + usage.getIp() + ":" + usage.getWavePath() + wave + "\n";
									fileOperationUtil.freePassword(fw, transContext, usage);
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
							fileOperationUtil.freePassword(fw, transContext, usage);
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
								fileOperationUtil.fileExist(usage.getWavePath() + "/" + labeledResult, executor);
							}
							String transContext1 = "scp " +  linuxPath + "/" + item.getWave() + ".wav " + usage.getLinuxUser() + "@" + usage.getIp() + ":" + usage.getWavePath() + "/" + labeledResult;
							fileOperationUtil.freePassword(fw, transContext1, usage);
							break;
						default:
							break;
						}
						wavetag.add(item.getWave());
					}
				}
				
				fw.close();
				if(labelType == 1){
					fileOperationUtil.fileExist(usage.getWavePath() + label, executor);
					//br.write("scp " + wavetagger.getAbsoluteFile() + "/wavetagger " + usage.getLinuxUser() + "@" + linux.getIp() + ":/" + linuxPath + "/label");
					executorPraat.exec1(wavetagger.getAbsolutePath(), wavetagger.getName(), usage.getWavePath() + label, 1);
				}else if (labelType == 0) {
					fileOperationUtil.fileExist(usage.getWavePath() + wave, executor);
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
	}
	
}
