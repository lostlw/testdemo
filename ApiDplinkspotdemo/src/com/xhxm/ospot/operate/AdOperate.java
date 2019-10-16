package com.xhxm.ospot.operate;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.xhxm.ospot.AdConfig;
import com.xhxm.ospot.AsyncImageLoader;
import com.xhxm.ospot.Util;
import com.xhxm.ospot.constant.AdConst;
import com.xhxm.ospot.file.SharedpreferencesOperate;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.model.AdRequestInfo;
import com.xhxm.ospot.model.DeviceInfo;

public class AdOperate {

	private Context mContext;

	public AdOperate(Context context) {
		this.mContext = context;
	}

	/**
	 * 初始化用户已安装的可投放的广告列表
	 * 
	 * @throws Exception
	 */
	public void initAdList() throws Exception {
		if (System.currentTimeMillis()
				- SharedpreferencesOperate.getInitime(mContext) > 60 * 1000) {
			String jscontent = Util.GetDataByGet(AdConfig.PKGURL);
			Util.Log(jscontent);
			JSONObject jsonObject = new JSONObject(jscontent);
			if (jsonObject.optInt("status", 0) == 1) {
				if (jsonObject.has("pkgs")) {
					JSONArray mJSONArray = jsonObject.optJSONArray("pkgs");
					StringBuffer chkappssb = new StringBuffer();
					for (int i = 0; i < mJSONArray.length(); i++) {
						String pkg = mJSONArray.getString(i);
						if (Util.isAppInstall(mContext, pkg)) {
							chkappssb.append(pkg);
							chkappssb.append(",");
						}
					}
					if (chkappssb.length() > 0) {
						chkappssb.deleteCharAt(chkappssb.length() - 1);
						SharedpreferencesOperate.setChkapps(mContext,
								chkappssb.toString());
					}
				}
				SharedpreferencesOperate.setInitime(mContext,
						System.currentTimeMillis());
			}
		}
	}

	public AdContent getAdTask(AdRequestInfo adRequestInfo) throws Exception {
		AdContent ads = null;
		String requestContent = getJsonRequestInfo(adRequestInfo);
		Util.Log(requestContent);
		// 获取正常广告
		String adcontent = Util.postHttpURLConnection(mContext,
				AdConfig.BASEURL, requestContent);
		Util.Log(" " + adcontent);

		if (!Util.isNull(adcontent)) {
			ads = getJsonReturnInfo(adcontent, true);
		}
		return ads;
	}

	// 将请求参数转换成json串
	public String getJsonRequestInfo(AdRequestInfo adRequestInfo) {
		String jsonRequestInfo = "";
		JSONObject alljson = new JSONObject();
		DeviceInfo deviceInfo = adRequestInfo.getDevice();
		try {

			JSONObject deviceJson = new JSONObject();
			deviceJson.put("did", deviceInfo.getDid());
			deviceJson.put("ime", deviceInfo.getIm());
			deviceJson.put("ims", deviceInfo.getSi());
			deviceJson.put("mcc", deviceInfo.getMcc());
			deviceJson.put("mnc", deviceInfo.getMnc());
			deviceJson.put("nw", deviceInfo.getNw());
			deviceJson.put("nt", String.valueOf(deviceInfo.getNt()));
			deviceJson.put("carrier", deviceInfo.getOperator());
			deviceJson.put("os", "android");
			deviceJson.put("os_ver", Build.VERSION.RELEASE);
			deviceJson.put("manu", Build.MANUFACTURER);
			deviceJson.put("model", deviceInfo.getModel());
			deviceJson.put("wh", deviceInfo.getWh());
			deviceJson.put("country", deviceInfo.getTcc());
			deviceJson.put("lan", "zh");

			deviceJson.put("ua", deviceInfo.getUa());
			deviceJson.put("ip", "1.119.140.114");// 公网IP,因是测试才硬编码写死

			String chkapps_str = SharedpreferencesOperate.getChkapps(mContext);
			if (!Util.isNull(chkapps_str)) {
				JSONArray chkappsArrays = new JSONArray();
				String[] strz = chkapps_str.split(",");
				for (int i = 0; i < strz.length; i++) {
					chkappsArrays.put(i, strz[i]);
				}
				deviceJson.put("checkapps", chkappsArrays);
			}
			alljson.put("device", deviceJson);
			alljson.put("test", "1");
			JSONObject app = new JSONObject();
			app.put("app_id", AdConfig.init().getAppId());
			app.put("slot_id", AdConfig.init().getSlotId());
			app.put("app_name", deviceInfo.getAppName());
			app.put("app_ver", deviceInfo.getAppVersion());

			alljson.put("app", app);

			jsonRequestInfo = alljson.toString();

			Log.d("log", "jsonRequestInfo " + jsonRequestInfo);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonRequestInfo;
	}

	// 将返回参数转换成对象
	public AdContent getJsonReturnInfo(String returnJson, boolean isSaveJsonData) {
		String msg = "";
		try {

			JSONObject adContentJson = new JSONObject(returnJson);
			int status = adContentJson.optInt("status", 0);
			if (status == AdConst.AD_HAVE) {
				// 有广告
				JSONObject ad = adContentJson.optJSONObject("ad");
				AsyncImageLoader asy = new AsyncImageLoader(mContext);
				AdContent adContent = new AdContent();
				if (null != ad) {
					adContent.setJsoninfo(returnJson);

					adContent.setDeepLink(ad.optString("deepLink", ""));
					adContent.setPkg(ad.optString("pkg", ""));

					JSONObject img = ad.optJSONObject("img");
					adContent.setImgLink(img.optString("imgLink", ""));
					adContent.setIconurl(img.optString("iconLink", ""));
					adContent.setImgDesc(img.optString("imgDesc", ""));

					JSONObject event = ad.optJSONObject("event");
					adContent.setClick(event.optString("click", ""));
					adContent.setShow(event.optString("show", ""));

					asy.loadBitmap(adContent.getImgLink());

					return adContent;
				}
			} else {
				msg = adContentJson.optString("msg", "");

			}
		} catch (JSONException e) {
			e.printStackTrace();
			msg = returnJson + e.getMessage();
		} finally {
			AdConfig.init().setMsg(msg);
		}
		return null;
	}

	public List<AdContent> getAdsTask(AdRequestInfo adRequestInfo)
			throws Exception {
		List<AdContent> ads = new ArrayList<AdContent>();
		// 将请求参数转成json串
		String requestContent = getJsonRequestInfo(adRequestInfo);
		// new BwriteLog(mContext).writeStr(requestContent, mContext);
		Util.Log("" + requestContent);
		String adcontent = null;
		// 获取正常广告
		adcontent = Util.postHttpURLConnection(mContext, AdConfig.LISTURL,
				requestContent);
		Util.Log(" " + adcontent);
		if (!Util.isNull(adcontent)) {
			ads = getAdList(adcontent);
		}
		return ads;
	}

	// 将返回参数转换成对象
	private List<AdContent> getAdList(String returnJson) {

		String msg = "";
		List<AdContent> adContents = new ArrayList<AdContent>();
		try {
			JSONObject adContentJson = new JSONObject(returnJson);
			int status = adContentJson.optInt("status", 0);
			// 如果开关 没有关闭则保存相关的信息，关闭则不进行操作
			if (status == AdConst.AD_HAVE) {
				// 有广告
				JSONArray ads = adContentJson.optJSONArray("ad");
				for (int i = 0; i < ads.length(); i++) {
					JSONObject ad = ads.getJSONObject(i);
					AsyncImageLoader asy = new AsyncImageLoader(mContext);
					AdContent adContent = new AdContent();
					if (null != ad) {
						adContent.setJsoninfo(returnJson);

						adContent.setDeepLink(ad.optString("deepLink", ""));
						adContent.setPkg(ad.optString("pkg", ""));
						adContent.setTitle(ad.optString("title", ""));
						adContent.setIconurl(ad.optString("iconLink", ""));

						adContent.setPrice(String.valueOf(ad.optDouble("price",
								0)));
						JSONObject event = ad.optJSONObject("event");
						adContent.setClick(event.optString("click", ""));
						adContent.setShow(event.optString("show", ""));

						asy.loadBitmap(adContent.getImgLink());

						adContents.add(adContent);
					}
				}
			} else {
				msg = adContentJson.optString("msg", "");

			}
		} catch (JSONException e) {
			e.printStackTrace();
			msg = returnJson + e.getMessage();
		} finally {
			AdConfig.init().setMsg(msg);
		}
		return adContents;
	}

	// 返回抢量对象

}
