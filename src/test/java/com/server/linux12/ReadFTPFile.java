    package com.server.linux12;
      
    import java.io.BufferedReader;  
    import java.io.FileNotFoundException;  
    import java.io.IOException;  
    import java.io.InputStream;  
    import java.io.InputStreamReader;  
    import java.net.SocketException;
import java.util.Collection;

import org.apache.commons.net.ftp.FTPClient;

import com.server.linux12.FTPUtil;

import eu.medsea.mimeutil.MimeUtil;  
      
    public class ReadFTPFile {  
      
        /** 
         * 去 服务器的FTP路径下上读取文件 
         *  
         * @param ftpUserName 
         * @param ftpPassword 
         * @param ftpPath 
         * @param FTPServer 
         * @return 
         */  
        public String readConfigFileForFTP(String ftpUserName, String ftpPassword,  
                String ftpPath, String ftpHost, int ftpPort, String fileName) {  
            StringBuffer resultBuffer = new StringBuffer();  
            InputStream in = null;  
            FTPClient ftpClient = null;  
            System.out.println("开始读取绝对路径" + ftpPath + "文件!");
            try {  
                ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName,  
                        ftpPort);  
                ftpClient.setControlEncoding("UTF-8"); // 中文支持  
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
                ftpClient.enterLocalPassiveMode();  
                ftpClient.changeWorkingDirectory(ftpPath);  
                in = ftpClient.retrieveFileStream(fileName);  
            } catch (FileNotFoundException e) {  
            	System.out.println("没有找到" + ftpPath + "文件");
                e.printStackTrace();  
                return "下载配置文件失败，请联系管理员.";  
            } catch (SocketException e) {  
            	System.out.println("连接FTP失败.");
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("文件读取错误。");
                e.printStackTrace();  
                return "配置文件读取失败，请联系管理员.";  
            }  
            if (in != null) {  
                BufferedReader br = new BufferedReader(new InputStreamReader(in));  
                String data = null;  
                try {  
                    while ((data = br.readLine()) != null) {  
                        resultBuffer.append(data + "\n");  
                    }  
                } catch (IOException e) {  
                	System.out.println("文件读取错误。");
                    e.printStackTrace();  
                    return "配置文件读取失败，请联系管理员.";  
                }finally{  
                    try {  
                        ftpClient.disconnect();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
                
/*
                MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
                Collection<?> mimeTypes = MimeUtil.getMimeTypes("ftp://127.0.0.2/demo.wave");
                System.out.println(mimeTypes);*/
            }else{  
            	System.out.println("in为空，不能读取。");
                return "配置文件读取失败，请联系管理员.";  
            }  
            return resultBuffer.toString();  
        }  
    }  
