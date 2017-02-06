package com.corpus.ssh;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RmtShellExecutor{
	private Connection conn; 
    private String     ip; 
    private String     usr;
    private String     psword; 
    private String     charset = Charset.defaultCharset().toString(); 
    private static final int TIME_OUT = 1000 * 5 * 60;
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPsword() {
		return psword;
	}

	public void setPsword(String psword) {
		this.psword = psword;
	}
	
	public RmtShellExecutor(String ip, String usr, String psword){
		this.ip = ip;
		this.usr = usr;
		this.psword = psword;
	}

	public boolean login() throws IOException { 
		conn = new Connection(ip);
	    conn.connect(); 
	    return conn.authenticateWithPassword(usr, psword); 
	}
	
	private String processStream(InputStream in, String charset) throws Exception { 
	    byte[] buf = new byte[1024]; 
	    StringBuilder sb = new StringBuilder(); 
	    while (in.read(buf) != -1) {
	    	sb.append(new String(buf, charset)); 
	    } 
	    return sb.toString(); 
	}
	
	public boolean exec() throws Exception {
        InputStream stdOut = null; 
        InputStream stdErr = null; 
        String outStr = ""; 
        String outErr = ""; 
        boolean ret = false; 
        try { 
            if (login()) { 
            	ret = true;
                return ret;
            } 
        } finally { 
            if (conn != null) {
                conn.close();
            } 
            IOUtils.closeQuietly(stdOut); 
            IOUtils.closeQuietly(stdErr); 
        } 
        return ret;
    } 
	
//	��������������
	public String exec_size(String cmd) throws Exception {
        InputStream stdOut = null; 
        InputStream stdErr = null; 
        String outStr = ""; 
        String outErr = ""; 
        try { 
            if (login()) { 
                Session session = conn.openSession(); 
                session.execCommand(cmd);
                
                stdOut = new StreamGobbler(session.getStdout()); 
                outStr = processStream(stdOut, charset); 
                
                stdErr = new StreamGobbler(session.getStderr()); 
                outErr = processStream(stdErr, charset); 
                
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT); 
                
                System.out.println("outStr=" + outStr); 
                System.out.println("outErr=" + outErr);
                
            } else { 
                throw new Exception(ip + "连接失败");
            } 
        } finally { 
            if (conn != null) {
                conn.close();
            } 
            IOUtils.closeQuietly(stdOut); 
            IOUtils.closeQuietly(stdErr); 
        } 
        return outStr;
    } 
	
	//执行服务器给定的cmd命令行
	public int serverCmd(String cmd){
		try {
			if(login()){
				return exec(cmd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//从本地上传文件至远程linux服务器
	
//��������
	//public int exec(String cmds) throws Exception {
	public int exec(String cmd) throws Exception {
        InputStream stdOut = null; 
        InputStream stdErr = null; 
        String outStr = ""; 
        String outErr = ""; 
        int ret = -1; 
        try { 
        	if(login()){
        		Session session = conn.openSession(); 
                session.execCommand(cmd);
                
                stdOut = new StreamGobbler(session.getStdout()); 
                outStr = processStream(stdOut, charset); 
                
                stdErr = new StreamGobbler(session.getStderr()); 
                outErr = processStream(stdErr, charset); 
                
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT); 
                
                System.out.println("outStr=" + outStr); 
                System.out.println("outErr=" + outErr);
                
                System.out.println("right");
                
                ret = session.getExitStatus(); 
        	}
                
        } finally { 
            if (conn != null) {
                conn.close();
            } 
            IOUtils.closeQuietly(stdOut); 
            IOUtils.closeQuietly(stdErr); 
        } 
        return ret; 
//        return outStr;
    } 
	//连接服务器
	public int exec1(String localFile, String filename, String remoteDir, int flag) throws Exception {
		//public int exec() throws Exception {
	        InputStream stdOut = null; 
	        InputStream stdErr = null; 
	        String outStr = ""; 
	        String outErr = ""; 
	        int ret = -1; 
	       // System.out.println(login());
	        try { 
	            if (login()) {	            	
	            	SCPClient client = new SCPClient(conn); 
	        		client.put(localFile, remoteDir); 
	        		switch (flag) {
					case 0:
						exec("chmod +x " + remoteDir + "/" + filename);
		        		exec("./" + remoteDir + "/" + filename);
						break;
					default:
						break;
					}
	            } else { 
	                throw new Exception("����������������" + ip); // ������������ ������ 
	            } 
	        } finally { 
	            if (conn != null) {
	                conn.close();
	            } 
	            IOUtils.closeQuietly(stdOut); 
	            IOUtils.closeQuietly(stdErr); 
	        } 
	        return ret; 
	 } 
	//��������tomcat
	public int exec2(String remoteDir, String localFile) throws Exception {
		//public int exec() throws Exception {
	    InputStream stdOut = null; 
	    InputStream stdErr = null; 
	    String outStr = ""; //shell的输出
	    String outErr = ""; 
	    int ret = -1; 
	    try { 
	        if (login()) { 
	            	
	        SCPClient client = new SCPClient(conn); 
	        client.get(remoteDir, localFile); 
	               
	        } else { 
	        throw new Exception("����������������" + ip); // ������������ ������ 
	        } 
	     } finally { 
	        if (conn != null) {
	           conn.close();
	        } 
	        IOUtils.closeQuietly(stdOut); 
	        IOUtils.closeQuietly(stdErr); 
	     } 
	     return ret; 
	} 
}
