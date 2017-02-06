package com.server.linux12;

import java.util.HashMap;

/*import com.server.linux.ReadFTPFile;  
import com.server.linux.WriteFTPFile;  */
  
public class FTPMain {
	//1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
	//1 2 1 2 3 2 3 4 5 4  5  6  7  8  7  8
    public static void main(String[] args) throws Exception {
    	
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("nihao", "english");
    	
    	/*File file = new File("E:\\20160530\\01717_1.wav");
    	InputStream inputStream = new FileInputStream(file);
    	BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
    	ByteArrayOutputStream out = new ByteArrayOutputStream(bufferedInputStream.available());
    	
    	int size = bufferedInputStream.available();
    	byte[] bts = new byte[size];
        
    	OutputStream oStream = new FileOutputStream(new File("E:\\20160530\\01717_2.wav"));
    	int length = 0;
    	byte[] temp = new byte[size];
    	int i = 0;
    	while((length = inputStream.read(bts))>0){
    		System.out.println(length);
    		for(int j = 0;j < length; i++,j++){
    			temp[i] = bts[j];
    		}
    	}
    	byte[] temp2 = new byte[size-44];
    	for(i = 44; i < temp.length; i++){
    		temp2[i-44] = temp[i];
    	}
    	byte[] temp3 = new byte[44];
    	for(i = 0; i < 44; i++){
    		temp3[i] = temp[i];
    	}

    	byte[] temp4 = new byte[size];
    	System.arraycopy(temp3, 0, temp4, 0, temp3.length);
    	System.arraycopy(temp2, 0, temp4, temp3.length, temp2.length);
    	
		oStream.write(temp4, 0, temp4.length);
    	System.out.println("完成");*/
    	/*File file = new File("E:\\20160530\\1235.txt");
    	byte[] bts = new byte[1024];
    	int length = 0;
    	try {
    	    InputStream stream = new FileInputStream(file);
    	    OutputStream os = new FileOutputStream(new File("E:\\20160530\\1234.txt"));
    	    while ((length = stream.read(bts)) > 0) {
    	    	System.out.println(stream.read(bts));
    	        os.write(bts, 0, length);
    	    }
    	    os.flush();
    	    os.close();
    	    stream.close();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}*/
    	
    	//jedis redis 测试
    	/*Jedis jedis = new Jedis("localhost");
    	jedis.set("mytest", "this is your test");
    	System.out.println(jedis.get("mytest"));*/
    	
    	//开线程
    	/*ThreadTest t1 = new ThreadTest("a", "你好");
    	t1.start();
    	
    	ThreadTest t2 = new ThreadTest("b", "hello");
    	t2.start();*/
    	
    	//写文件
    	/*File file = new File("label.sh");
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	System.out.println(file.getAbsolutePath());
    	FileWriter fileWriter = new FileWriter(file);
    	fileWriter.write("echo love\n");
    	fileWriter.write("echo hate\n");
    	fileWriter.close();
    	
    	RmtShellExecutor executor = new RmtShellExecutor("159.226.177.248", "lala", "ynn520");
    	executor.exec1(file.getAbsolutePath(), file.getName(), "Desktop", 0);*/
    	
    	
    	
    	/*int a = 0x02;
    	System.out.println(a * Math.pow(2, 8));*/

    	
    	/*File file = new File("E:/corpusTest/01717_1.wav");
    	
    	byte[] b = new byte[128];
    	RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
    	randomAccessFile.seek(0);
    	randomAccessFile.read(b);
    	randomAccessFile.close();
    	for(int i = 0; i < b.length; i++){
    		System.out.println(b[i] + "+" + i);
    	}*/
    	
    	
    	     /*   File file = new File("E:/corpusTest/media01.wav");
    	        Clip clip = AudioSystem.getClip();
    	        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
    	        clip.open(ais);
    	        System.out.println( clip.getMicrosecondLength() / 1000000D + " s" );//获取音频文件时长
*/    
    	
    	/*String data = "nihao name:123 wobuzhidao";
    	String temp = data.replaceAll("\\s*", "");
    	System.out.println(temp);
		
		String pattern = "name:(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(temp);
        if (m.find( )) {
           System.out.println("Found value: " + m.group(0) );
           System.out.println("Found value: " + m.group(1) );
           System.out.println("Found value: " + m.group(2) );
        } else {
           System.out.println("NO MATCH");
        }*/
        /*int ftpPort = 21;  
        String ftpUserName = "ynn";  
        String ftpPassword = "123";  
        String ftpHost = "127.0.0.1";  
        String ftpPath = ".";*/
        /*String writeTempFielPath = "1234.txt";  */
        /*try { 
            
               ftpUserName = properties.getProperty("ftpUserName");  
                ftpPassword = properties.getProperty("ftpPassword");  
                ftpHost = properties.getProperty("ftpHost");  
                ftpPort = Integer.valueOf(properties.getProperty("ftpPort"))  
                        .intValue();  
                ftpPath = properties.getProperty("ftpPath");
                

            	FTPClient ftpClient = FTPUtil.getFTPClient("159.226.177.161", "ynn520", "lala", 21);  
            	FTPClient ftpClient1 = FTPUtil.getFTPClient("159.226.177.161", "ynn520", "lala", 21);  
                ftpClient.setControlEncoding("UTF-8"); // 中文支持  
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
                ftpClient.enterLocalPassiveMode();  
                //ftpClient.changeWorkingDirectory(ftpPath);  
            	FTPFile[] files = ftpClient.listFiles("Desktop/corpusFile/wave");
            	FTPFile[] files1 = ftpClient.listFiles("Desktop/corpusFile/wave/german");
            	
            	List<String> fileNames = new ArrayList<String>();
            	
            	for(int i = 0; i < files.length; i++){
            		if(!files[i].isDirectory()){
            			if(ftpClient.isConnected()){
            				fileNames.add(files[i].getName());
            				InputStream in = null;
                			System.out.println(files[i].getName());
                			ftpClient.enterLocalPassiveMode();
                			in = ftpClient.retrieveFileStream("labelFile/" + files[i].getName());
                			            				
                			if(in == null){
                				System.out.println("文件为空");
                			}else{
                				BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    			String data = "";
                    			while ((data = br.readLine()) != null) {
        							System.out.println(data);
        						}
                			}
                			
                			ftpClient.disconnect();
                			ftpClient = ftpClient1; 
            			}
            		}
            	}*/
            	
               /* writeTempFielPath = properties.getProperty("writeTempFielPath");  */
                  
               /*ReadFTPFile read = new ReadFTPFile();  
                String result = read.readConfigFileForFTP(ftpUserName, ftpPassword, ftpPath, ftpHost, ftpPort, "1235.txt");  
                System.out.println("读取配置文件结果为：" + result);
                  */
                /*WriteFTPFile write = new WriteFTPFile();  
                write.upload(ftpPath, ftpUserName, ftpPassword, ftpHost, ftpPort, result, writeTempFielPath);*/
        	
        	
        	/*for(int i = 0; i < files.length; i++){
        		if(files[i].isFile()){
        			System.out.println(files[i].getName());
        		}else if (files[i].isDirectory()) {
					System.out.println("目录：" + files[i].getName());
				}
        	}
       
        } catch (Exception e) {
            e.printStackTrace();  
        }*/
    }  
}