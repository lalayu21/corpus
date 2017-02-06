package com.corpus.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.corpus.entity.Corpus;
import com.corpus.utils.LeadCorpus;

public class LeadinCorpus extends Thread {
	
	public void run(){
		long intervalTime = 0;
		
		long period = 2000000;
		
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//连接数据库
				QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
				
				//获取待创建的语料库id
				String sql = "select id, createtime from corpus where flag = 0 order by createtime";
				List<Corpus> idList = new ArrayList<Corpus>();
				try {
					idList = queryRunner.query(sql, new BeanListHandler<Corpus>(Corpus.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("轮询获取未导入的语料库时失败");
					e.printStackTrace();
				}
				
				if(idList != null){
					for(Corpus iterm : idList){
						int id = iterm.getId();
						LeadCorpus leadCorpus = new LeadCorpus(id, queryRunner);
						leadCorpus.start();
					}
				}
			}
		}, intervalTime, period);
	}
}
