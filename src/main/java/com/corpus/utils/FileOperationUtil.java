package com.corpus.utils;

import java.io.File;
import java.io.FileWriter;

import com.corpus.entity.Usage;
import com.corpus.ssh.RmtShellExecutor;

public class FileOperationUtil {

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
