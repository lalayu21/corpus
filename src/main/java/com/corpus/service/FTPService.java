package com.corpus.service;

import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.FtpConnect;

public interface FTPService {
	public FTPClient getFtpClient(FtpConnect ftpConnect);
	
}
