package com.xhxm.ospot.task;



import android.content.Context;

import com.xhxm.ospot.Util;
/**
 * 
* <p>Title: PostLogTask</p>
* <p>Description:提交日志异步 </p>
 */
public class PostLogThread extends Thread{

	private String mUrl;
	private String log;
	private int type;
	
	
	public PostLogThread(Context context, String url,int type){
		this.mUrl = url;
		this.type = type;
	}

	@Override
	public void run() {
		super.run();
		synchronized (this) {
			try {
				Util.Log("post log:" + mUrl);
				log = "success " +Util.GetDataByGet(mUrl);
			} catch (Exception e) {
				e.printStackTrace();
				log = "error " + e.getMessage();
			}
			Util.Log("post result :" + log);
		}
	}

}
