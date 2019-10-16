package com.xhxm.ospot.util;

import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.webkit.WebView;

public class DeviceUtil {

	public static int mNetWorkType = 0;

	/** 没有网络 */
	public static final int NETWORKTYPE_INVALID = 0;

	/** wifi网络 */
	public static final int NETWORKTYPE_WIFI = 1;

	/** 2G网络 */
	public static final int NETWORKTYPE_2G = 2;

	/** 3G和3G以上网络，或统称为快速网络 */
	public static final int NETWORKTYPE_3G = 3;

	public static final int NETWORKTYPE_4G = 4;



	 
	public static String getUa(Context context) {
		return new WebView(context).getSettings().getUserAgentString();
	}
	
	public static int getVersionCode(Context paramContext) {
		try {
			int i = paramContext.getPackageManager().getPackageInfo(
					paramContext.getPackageName(), 0).versionCode;
			return i;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return -1;
	}

	public static String getVersionName(Context paramContext) {
		try {
			String str = paramContext.getPackageManager().getPackageInfo(
					paramContext.getPackageName(), 0).versionName;
			return str;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return "";
	}

	public static int haveSimCard(Context paramContext) {
		return ((TelephonyManager) paramContext.getSystemService("phone"))
				.getSimState() == TelephonyManager.SIM_STATE_READY ? 1:0;

	}
	
	/**获取手机制造商*/
	public static String getPhoneManufacturer(){
		return android.os.Build.MANUFACTURER;
	}

	public static boolean isNetworkConnected(Context paramContext) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getActiveNetworkInfo();
			if (localNetworkInfo != null) {
				return localNetworkInfo.isAvailable();
			}
		}
		return false;
	}



	
	public static String getLanguageCode() {
		return Locale.getDefault().getLanguage();
	}

	public static String getCountryCode(Context paramContext) {
		return paramContext.getResources().getConfiguration().locale
				.getCountry();
	}


	public static int GetNetworkType(Context context) {
		String strNetworkType = "";

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				strNetworkType = "WIFI";
				mNetWorkType = NETWORKTYPE_WIFI;
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				String _strSubTypeName = networkInfo.getSubtypeName();

				// TD-SCDMA networkType is 17
				int networkType = networkInfo.getSubtype();
				switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
															// 11
					strNetworkType = "2G";
					mNetWorkType = NETWORKTYPE_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
															// 14
				case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
															// 12
				case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
															// 15
					strNetworkType = "3G";
					mNetWorkType = NETWORKTYPE_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
														// 13
					strNetworkType = "4G";
					mNetWorkType = NETWORKTYPE_4G;
					break;
				default:
					// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
					if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
							|| _strSubTypeName.equalsIgnoreCase("WCDMA")
							|| _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
						strNetworkType = "3G";
						mNetWorkType = NETWORKTYPE_3G;
					} else {
						strNetworkType = _strSubTypeName;
					}

					break;
				}

			}else{
				mNetWorkType = NETWORKTYPE_INVALID;
			}
		}else{
			mNetWorkType = NETWORKTYPE_INVALID;
		}

		return mNetWorkType;
	}
	
	
	public static String getUniquely(Context context) {
		String sUniquelyCode = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			sUniquelyCode = tm.getDeviceId(); // 模拟器则返回00000000000
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sUniquelyCode;
	}
	
	
	public static String getAndroid_ID(Context context) {
		String sUniquelyCode = Secure.getString(  
        context.getContentResolver(), Secure.ANDROID_ID);  
		return sUniquelyCode;
	}
	
	  public static String getMCC(Context paramContext)
	  {
	    String str = ((TelephonyManager)paramContext.getSystemService("phone")).getSimOperator();
	    if ((str != null) && (str.length() > 3)) {
	      return str.substring(0, 3);
	    }
	    return "";
	  }
	  
	  /**
	   * 
	  * <p>Title: getMNC</p>
	  * <p>Description: 
00, "CHINA MOBILE", "CN" 中国移动
01, "CHN-CUGSM", "CN" 中国联通
02, "CHINA MOBILE", "CN" 中国移动 （TD）
03, "CHINA TELECOM", "CN" 中国电信</p>
	  * @param paramContext
	  * @return
	   */
	  public static String getMNC(Context paramContext)
	  {
	    String str = ((TelephonyManager)paramContext.getSystemService("phone")).getSimOperator();
	    if ((str != null) && (str.length() > 3)) {
	      return str.substring(3, str.length());
	    }
	    return "";
	  }

	  /**
	   * 
	  * <p>Title: getOperator</p>
	  * <p>Description:SIM卡运营商 </p>
	  * @param paramContext
	  * @return
	   */
	  public static String getOperator(Context paramContext)
	  {
		 TelephonyManager tm = (TelephonyManager)paramContext
					.getSystemService(Context.TELEPHONY_SERVICE);
		 return tm.getSimOperatorName();
	  }
	  
	  

}
