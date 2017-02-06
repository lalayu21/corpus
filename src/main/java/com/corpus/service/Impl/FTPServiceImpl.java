package com.corpus.service.Impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import com.corpus.entity.FtpConnect;
import com.corpus.service.FTPService;
import com.server.linux.FTPUtil;




@Service
public class FTPServiceImpl implements FTPService {
	

	@Override
	public FTPClient getFtpClient(FtpConnect ftpConnect) {

		FTPClient ftpClient = FTPUtil.getFTPClient(ftpConnect.getIp(), ftpConnect.getPassword(), ftpConnect.getUsername(), ftpConnect.getPort());
		String ftpPath = ftpConnect.getPath();
		try {
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
		}catch (FileNotFoundException e) {  
        	System.out.println("没有找到" + ftpPath + "文件");
            e.printStackTrace();  
        } catch (SocketException e) {  
        	System.out.println("连接FTP失败.");
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("文件读取错误。");
            e.printStackTrace();  
        }
		return ftpClient;
	}


}
