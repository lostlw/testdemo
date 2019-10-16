package com.xhxm.ospot.task;

import com.xhxm.ospot.operate.AdOperate;

import android.content.Context;

public class InitAdThread extends Thread {

	private Context mContext;

	public InitAdThread(Context context) {
		this.mContext = context;
	}

	private static Object obj = new Object();

	@Override
	public void run() {
		super.run();
		synchronized (obj) {
			try {
				
				new AdOperate(mContext).initAdList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
