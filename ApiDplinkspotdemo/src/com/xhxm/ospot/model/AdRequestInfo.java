package com.xhxm.ospot.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.xhxm.ospot.AdConfig;
import com.xhxm.ospot.Util;
import com.xhxm.ospot.util.DeviceUtil;
import com.xhxm.ospot.util.UIUtils;

/**
 * 
 * <p>
 * Title: AdRequestInfo
 * </p>
 * <p>
 * Description:请求参数
 * </p>
 * 
 * @date 2018-11-8
 */
public class AdRequestInfo {
	/** 设备信息 */
	private DeviceInfo device;

	private static Context mcontext;
	public static AdRequestInfo adRequestInfo;

	public void getInstance(Context context) throws Exception {
		if (null == adRequestInfo) {
			adRequestInfo = new AdRequestInfo();
		}
		mcontext = context;
		initParams();
	}

	private AdRequestInfo initParams() {
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setOv(Build.VERSION.RELEASE);
		PackageManager pm = mcontext.getPackageManager();
		PackageInfo pkgInfo;
		try {
			pkgInfo = pm.getPackageInfo(mcontext.getPackageName(), 0);
			if (pkgInfo != null) {
				deviceInfo.setPn(mcontext.getPackageName());// 应用包名
				deviceInfo.setVn(pkgInfo.versionName);// 应用版本
				deviceInfo.setVc(pkgInfo.versionCode);
				deviceInfo.setAppVersion(pkgInfo.versionName);
				deviceInfo.setAppName(String.valueOf(pkgInfo.applicationInfo.loadLabel(pm)));
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		TelephonyManager telephonyManager = (TelephonyManager) mcontext.getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			deviceInfo.setIm(DeviceUtil.getUniquely(mcontext));
			deviceInfo.setSi(telephonyManager.getSubscriberId());// imsi码
			deviceInfo.setMnc(DeviceUtil.getMNC(mcontext));// 移动网络号码
			deviceInfo.setMcc(DeviceUtil.getMCC(mcontext));// 移动国家码
		} catch (Exception e) {
			e.printStackTrace();
		}

		deviceInfo.setDid(DeviceUtil.getAndroid_ID(mcontext));
		deviceInfo.setBrand(Build.MANUFACTURER);// 制造商
		deviceInfo.setModel(Build.MODEL);// 机型
		deviceInfo.setWh(UIUtils.getmScreenWidth(mcontext) + "x" + UIUtils.getmScreenHeight(mcontext));

		deviceInfo.setNw(Util.netWorkType(mcontext));// 网络接入点
		deviceInfo.setUa(DeviceUtil.getUa(mcontext));

		deviceInfo.setNt(DeviceUtil.GetNetworkType(mcontext));// 网络连接
		deviceInfo.setSim(DeviceUtil.haveSimCard(mcontext));// sim 1有 0无
		deviceInfo.setManufacturer(DeviceUtil.getPhoneManufacturer());// 手机制造商
		deviceInfo.setTcc(DeviceUtil.getCountryCode(mcontext));// 手机国家码
		deviceInfo.setOperator(DeviceUtil.getOperator(mcontext));// 运营商

		adRequestInfo.setDevice(deviceInfo);
		
		return adRequestInfo;

	}


	public DeviceInfo getDevice() {
		return device;
	}

	public void setDevice(DeviceInfo device) {
		this.device = device;
	}



}
