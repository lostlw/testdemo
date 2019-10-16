package com.xhxm.ospot;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.xhxm.ospot.util.Base64;

public class Util {


	public static boolean testTag = true;

	public static void Log(String TAG, String messageString) {
		if (testTag) {
			android.util.Log.i(TAG, messageString);
		}
	}

	// public static void openDeepLinkApp(Context context,String uriStr){
	// Intent intent = new Intent(Intent.ACTION_VIEW);
	// intent.addCategory(Intent.CATEGORY_BROWSABLE);
	// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// intent.setData(Uri.parse(uriStr));
	// try {
	// context.startActivity(intent);
	// } catch (ActivityNotFoundException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 打开DeepLink拉活app,并上报日志
	 * 
	 * @param context
	 * @param uriStr
	 * @param ad
	 */
	public static void openDeepLinkApp(Context context, String uriString, String packageName)
			throws Exception {

		Intent intent = Intent.parseUri(uriString, Intent.URI_INTENT_SCHEME);
		intent.setPackage(packageName);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (resolveInfo != null) {
			try {
				context.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			// 拉活APP后，调用track的active_urls字段中的链接上报状态
		}else{
			throw new Exception("resolveInfo is null");
		}  
	}




	public static void Log(String messageString) {
		Log("os", messageString);
	}

	public static void SendMSG(Handler paramHanler, int paramMsgID, Object paramInfo) {
		Message msg = paramHanler.obtainMessage(paramMsgID);
		msg.obj = paramInfo;
		msg.sendToTarget();
	}

	public static boolean checkNetWork(Context context) {
		NetworkInfo networkinfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return networkinfo != null ? (networkinfo.isAvailable() && networkinfo.isConnected()) : false;
	}

	public static boolean isNull(String obj) {
		return obj == null || obj.trim().length() == 0 || "null".equals(obj) || "{}".equals(obj)
				|| "[]".equals(obj);
	}

	public static String postHttpURLConnection(Context context, String url, String params) throws Exception {

		String result = "";
		HttpURLConnection connection = null;
		InputStream is = null;
		OutputStream dos = null;
		try {
			if (connection == null) {
				connection = (HttpURLConnection) new URL(url).openConnection();
			}
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);

			connection.setConnectTimeout(15 * 1000);
			dos = connection.getOutputStream();

			byte[] b = params.getBytes();

			dos.write(b);
			dos.flush();
			int code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
			} else {
				Util.Log("code is  " + code + "  " + url);
				is = connection.getInputStream();
			}
			if (is != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 5012);
				String data = "";
				StringBuilder sb = new StringBuilder();
				while ((data = br.readLine()) != null) {
					sb.append(data);
				}
				result = sb.toString();
				// Util.Log( result);
			} else {
				Util.Log("post error");
			}
		} catch (Exception e) {
			Util.Log(e.getMessage());
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}

	public static String netWorkType(Context context) {
		String network_type = "";
		try {
			ConnectivityManager conManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info_mobile = conManager.getActiveNetworkInfo();
			if (info_mobile != null && info_mobile.getState() == State.CONNECTED) {
				if(info_mobile.getType() == ConnectivityManager.TYPE_WIFI){
					network_type = "wifi";
				}else{
					network_type = info_mobile.getExtraInfo();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return network_type;
	}

	public static Drawable readDrawable(Context context,String str) {
		Drawable d = new ColorDrawable(Color.TRANSPARENT);
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(Base64.decode(str)));
			
//			d = new BitmapDrawable(bitmap);
			d = new BitmapDrawable(context.getResources(),bitmap);
		} catch (Exception e) {
		}
		return d;
	}



	public static String utf8Encode(String paramString) {
		if (!isEmpty(paramString)) {
			try {
				return URLEncoder.encode(paramString, "UTF-8");
			} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
				localUnsupportedEncodingException.printStackTrace();
			}
		}
		return paramString;
	}

	public static boolean isEmpty(String paramString) {
		return (paramString == null) || (paramString.length() == 0);
	}


	private static final int BUFFER_SIZE = 8 * 1024;

	public static void saveImgFile(Context context, String url, File file) {
		HttpURLConnection connection = null;
		InputStream is = null;
		OutputStream dos = null;
		file = new File(file.getAbsolutePath() + ".tmp");
		try {
			if (connection == null) {
				connection = (HttpURLConnection) new URL(url).openConnection();
			}
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10 * 1000);
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
				dos = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int n = 0;
				while ((n = is.read(buffer)) > 0) {
					dos.write(buffer, 0, n);
				}

			}
			String newfile = file.getAbsolutePath().replace(".tmp", "");
			file.renameTo(new File(newfile));
		} catch (Exception e) {
			if (file.exists()) {
				file.delete();
			}
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String GetDataByGet(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10 * 1000);
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader bufReader = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), "utf-8"));
				String backData = "";
				String line = "";
				while ((line = bufReader.readLine()) != null){
					backData += line;
				}
				return backData;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String MD5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	public static String getDlpath(Context ctx) {
		String downpath = "";
		String path = "";
		boolean isSdcard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (isSdcard) {
			// String s1 = ctx.getPackageName().replace("a", "o");
			// int length = s1.length() < 10 ? s1.length() : s1.length()/2;
			// String fname = Util.MD5(s1).substring(length);
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
			downpath = path + "/download/" + "durian" + "/";
		} else {
			File cacheDir = ctx.getCacheDir();
			path = cacheDir.getPath();
			downpath = path + "/";
		}
		return downpath;
	}

	public static String getDlfileName(String apkUrl) {

		return Util.MD5(apkUrl);
	}

	/**
	 * 根据包名启动应用
	 * */
	public static void startApp(Context context, String apkName) {
		// Toast.makeText(context, apkName, Toast.LENGTH_LONG).show();

		try {
			PackageManager packageManager = context.getPackageManager();
			Intent intent = new Intent();
			intent = packageManager.getLaunchIntentForPackage(apkName);
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static boolean isAppInstall(Context context, String pkgname) {

		if (isNull(pkgname)) {
			return false;
		}

		boolean is = true;
		try {
			PackageInfo p = context.getPackageManager().getPackageInfo(pkgname, 0);
			if (p == null) {
				is = false;
			}
		} catch (NameNotFoundException e) {
			is = false;
		}
		return is;
	}

	/**
	 * 查询用户手机内所有非系统并已安装应用，获取非系统并已安装应用的包信息，通过包信息返回相应包名列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getAllApps(Context a) {
		PackageManager pManager = a.getPackageManager();
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		try {
			// 获取手机内所有应用
			List<PackageInfo> paklist = pManager.getInstalledPackages(0);
			for (int i = 0; i < paklist.size(); i++) {
				PackageInfo pak = (PackageInfo) paklist.get(i);
				// 判断是否为非系统预装的应用程序
				if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
					apps.add(pak);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getAllApp(a, apps, pManager);
	}

	/**
	 * 获取到所有安装的应用包名
	 */
	private static List<String> getAllApp(Context context, List<PackageInfo> apps, PackageManager pManager) {
		List<String> lsi = new ArrayList<String>();
		try {
			for (int i = 0; i < apps.size(); i++) {
				PackageInfo pi = apps.get(i);
				ApplicationInfo ainfo = pi.applicationInfo;
				lsi.add(ainfo.packageName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsi;
	}

	/**
	 * 外弹需过滤的应用
	 * 
	 * @param a
	 * @return
	 */
	public static List<String> getFilterApps(Context a) {
		List<String> names = new ArrayList<String>();
		PackageManager pManager = a.getPackageManager();
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		try {
			// 获取手机内所有应用
			List<PackageInfo> paklist = pManager.getInstalledPackages(0);
			for (int i = 0; i < paklist.size(); i++) {
				PackageInfo pak = (PackageInfo) paklist.get(i);
				// 判断是否为非系统预装的应用程序
				if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) > 0) {
					apps.add(pak);
				}
			}
			names.add(a.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		names.addAll(getAllApp(a, apps, pManager));
		names.addAll(getHomes(a));
		return names;
	}

	/**
	 * 获得属于桌面的应用的应用包名称
	 * 
	 * @return 返回包含所有包名的字符串列表
	 */
	public static List<String> getHomes(Context context) {
		List<String> names = new ArrayList<String>();
		try {
			PackageManager packageManager = context.getPackageManager();
			// 属性
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
			for (ResolveInfo ri : resolveInfo) {
				names.add(ri.activityInfo.packageName);
			}
		} catch (Exception e) {
		}
		return names;
	}

	/**
	 * 5.0版本获取当前顶层应用,6.0已失效
	 * 
	 * @param context
	 * @return
	 */
	public static String getRunningAppProcesses(Context context) {

		ActivityManager mActivityManager = (ActivityManager) context.getSystemService("activity");
		final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
			if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				List<String> pkgs = Arrays.asList(processInfo.pkgList);
				if (pkgs != null && pkgs.size() > 0) {
					return pkgs.get(0);
				}
			}
		}
		return "";
	}

	public static String MD5fileName(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString().substring(0, 8);
	}

	/**
	 * 加密数据
	 * 
	 * @param temp
	 * @return
	 */
	public static String crypEncode(String temp) {
		String result = "";
		if (isNull(temp)) {
			return result;
		}
		try {
			result = new String(Base64.encode((temp.getBytes())));
		} catch (Exception e) {
		}
		result = result.replaceAll("=", "\\.");
		return result;
	}


	/**
	 * 本地解密数据
	 * 
	 * @param temp
	 * @return
	 */
	public static String crypDecodeLocal(String temp) {

		String result = "";
		if (isNull(temp)) {
			return result;
		}

		temp = temp.replaceAll("\\.", "=");
		try {
			result = new String((Base64.decode(temp)));
		} catch (Exception e) {
		}
		return result;
	}



	/***
	 * 
	 * <p>
	 * Title: openBrowserUrl
	 * </p>
	 * <p>
	 * Description: 使用网页浏览器 开启广告url
	 * </p>
	 * 
	 * @param paramContext
	 * @param paramString
	 */
	public static void openBrowserUrl(Context paramContext, String url) {
		if ((url == null) || (paramContext == null)) {
			return;
		}
		if (url.startsWith("@")) {
			url = url.substring(1);
		}
		try {
			List<?> localList = paramContext.getPackageManager().queryIntentActivities(
					new Intent("android.intent.action.VIEW", Uri.parse("http://www.baidu.com")), 65536);
			if ((localList != null) && (localList.size() != 0)) {
				ActivityInfo localActivityInfo = ((ResolveInfo) localList.get(0)).activityInfo;
				Intent localIntent1 = new Intent("android.intent.action.VIEW", Uri.parse(url));
				localIntent1.addFlags(268435456);

				localIntent1.setClassName(localActivityInfo.packageName, localActivityInfo.name);
				paramContext.startActivity(localIntent1);
			}
		} catch (Exception e) {
			try {
				Intent localIntent2 = new Intent("android.intent.action.VIEW", Uri.parse(url));
				localIntent2.addFlags(268435456);
				paramContext.startActivity(localIntent2);
			} catch (Exception localException2) {
				localException2.printStackTrace();
			}
		}
	}

	/**
	 * 获取用户手机已有浏览器
	 * 
	 * @param paramContext
	 * @return
	 */
	public static String getAvailableBrowserActivity(Context paramContext) {
		String localObject = "";
		try {
			List<?> localList = paramContext.getPackageManager().queryIntentActivities(
					new Intent("android.intent.action.VIEW", Uri.parse("http://www.baidu.com")), 65536);
			if ((localList != null) && (localList.size() != 0)) {
				ActivityInfo localActivityInfo = ((ResolveInfo) localList.get(0)).activityInfo;
				localObject = localActivityInfo.packageName + "/" + localActivityInfo.name;
			}
			return localObject;
		} catch (Throwable localThrowable) {
		}
		return null;
	}


	// 判断是否有某种权限
	public static boolean hasPermission(Context context, String permission) {
		return context.getPackageManager().checkPermission(permission, context.getPackageName()) == 0;
	}

	/**
	 * 不可更改,用于记录每天相关记录次数
	 * 
	 * @return
	 */
	public static String getCurDay() {
		String value = "";
		try {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
			value = sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getBefDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		Date date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return df.format(date);
	}

	/**
	 * 兼容setBackGround
	 */
	@SuppressLint("NewApi")
	public static void setBackgroud(View view, Drawable background) {
		if (Build.VERSION.SDK_INT >= 16) {
			view.setBackground(background);
		} else {
			view.setBackgroundDrawable(background);
		}
	}

	public static String getAppName(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pkgInfo;
		try {
			pkgInfo = pm.getPackageInfo(context.getPackageName(), 0);
			return String.valueOf(pkgInfo.applicationInfo.loadLabel(pm));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}// 包信息
		return "";
	}

	/**
	 * 对大小进行算换
	 */
	public static String convertFileSize(int size) {
		long kb = 1024;
		long mb = 1024 * kb;
		long gb = 1024 * mb;
		if (size >= gb) {
			return String.format("%.2f GB", (float) size / gb);
		} else if (size >= mb) {
			return String.format("%.2f MB", (float) size / mb);
		} else if (size >= kb) {
			return String.format("%.2f KB", (float) size / kb);
		} else {
			return String.format("%d B", size);
		}
	}

	public static void runInstall(Context ctx, String newfile) {
		if (ctx != null) {
			try {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				if (Build.VERSION.SDK_INT < 24 || ctx.getApplicationInfo().targetSdkVersion < 24) {
					intent.setDataAndType(Uri.parse("file://" + newfile),
							"application/vnd.android.package-archive");
				} else {
					// 声明需要的零时权限
					intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					// 第二个参数,即第一步中配置的authorities
					String fileProvider = ctx.getPackageName() + ".FileProvider";
					Uri contentUri = FileProvider.getUriForFile(ctx, fileProvider, new File(newfile));
					intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
				}
				ctx.startActivity(intent);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isProviderAuthorityConfig(Context ctx) {
		try {
			PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_PROVIDERS);
			ProviderInfo[] providers = info.providers;
			if (providers != null) {
				for (ProviderInfo provider : providers) {
					// Log( "name is " + provider.name);
					return provider.authority.equals(ctx.getPackageName() + ".FileProvider");
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
	/**
	 * dip转换为px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转换为dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
