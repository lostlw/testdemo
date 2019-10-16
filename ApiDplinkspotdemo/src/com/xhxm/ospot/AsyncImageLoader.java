package com.xhxm.ospot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

/**
 * 处理图片,加载图片用
 * @author abc
 */
public class AsyncImageLoader {
	
	

	public static HashMap<String, SoftReference<Bitmap>> imageCache= new HashMap<String, SoftReference<Bitmap>>();;
	private ThreadPoolExecutor executor;
	private static File cacheFile = null;
	private Context context;
	

	public AsyncImageLoader(Context context) {
		this.context = context;
		cacheFile = getimgFilePath(context);
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(1, 50, 180, TimeUnit.SECONDS, queue);
	}

	/**
	 * 异步加载图片,回调显示
	 * 
	 * @param imageUrl
	 * @param imageCallback
	 * @return
	 */
	public Bitmap loadBitmap(final String imageUrl, final ImageCallback imageCallback) {
		Bitmap drawable = null;

		if (imageCache != null && imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			drawable = softReference.get();
			if (drawable != null && !drawable.isRecycled()) {
				return drawable;
			}
		}

		final Handler handler = new Handler(context.getMainLooper()) {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
			}
		};
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Bitmap mdrawable = loadImageFromUrl(context, imageUrl);
				if (mdrawable != null && !mdrawable.isRecycled()) {
					imageCache.put(imageUrl, new SoftReference<Bitmap>(mdrawable));
					if (imageCallback != null) {
						Message message = handler.obtainMessage(0, mdrawable);
						handler.sendMessage(message);
					}
				}
			}
		});
		return drawable;
	}

	/**
	 * 预下载图片到本地
	 * 
	 * @param imageUrl
	 */
	public void loadBitmap(final String imageUrl) {
		loadImageFromUrl2local(context, imageUrl);
	}

	/**
	 * 联网加载图片
	 * 
	 * @param context
	 * @param imageUrl
	 * @return
	 */
	public Bitmap loadImageFromUrl(Context context, String imageUrl) {
		Bitmap bicon = null;
		try {
			if (imageUrl == null || "".equals(imageUrl)) {
				return null;
			}
			String fileName = getFilePath(imageUrl);
			cacheFile = getimgFilePath(context);
			File file = new File(cacheFile, fileName);// 保存文件
			if (!file.exists() && !file.isDirectory()) {
				loadimg(imageUrl, file);
			} else {
				
			}
			bicon = BitmapFactory.decodeFile(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bicon;
	}

	/**
	 * 联网加载图片
	 * 
	 * @param context
	 * @param imageUrl
	 * @return
	 */
	public void loadImageFromUrl2local(Context context, String imageUrl) {
		try {
			if (imageUrl == null || "".equals(imageUrl)) {
				return;
			}
			String fileName = getFilePath(imageUrl);
			cacheFile = getimgFilePath(context);
			File file = new File(cacheFile, fileName);// 保存文件
			if (!file.exists() && !file.isDirectory()) {
				loadimg(imageUrl, file);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载本地下载过的图片
	 * @param imageUrl
	 * @param file
	 */
	public static Bitmap loadImageFromLocal(Context context, String imageUrl) {
		Bitmap bicon = null;
		String fileName = "";
		if (imageUrl != null && imageUrl.length() != 0) {
			fileName = getFilePath(imageUrl);
		} else {
			return null;
		}
		File cacheFile = null;
		// 图片在手机本地的存放路径,注意：fileName为空的情况
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheFile = new File(Environment.getExternalStorageDirectory().getPath() + PATH);
			if (!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			cacheFile = context.getCacheDir();
		}
		File file = new File(cacheFile, fileName);// 保存文件
		if (!file.exists() && !file.isDirectory()) {
			return null;// 图片不存在返回null
		} else {
			try {
				if (file.length() < 1) {// 有可能是无效图片,返回null
					return null;
				}
				bicon = BitmapFactory.decodeFile(file.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bicon;
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}

	private void loadimg(String imageUrl, File file) {
		try {
			Util.saveImgFile(context, imageUrl, file);
	
		} catch (Exception e) {
			if (file.exists()) {
				file.delete();
			}
			
		}
	}

	public void recyled() {
		imageCache = null;
		executor = null;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param f
	 * @return
	 */
	private static int getAppdoublesize(File f) {
		FileInputStream fis = null;
		int d = 0;
		try {
			fis = new FileInputStream(f);
			d = fis.available();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return d;
	}


	public static String getFilePath(String s) {
		if (s == null || "".equals(s))
			throw new RuntimeException("Null url passed in");
		return Util.MD5(s).substring(1, 10);
	}

	/**
	 * recycle bitmap
	 */
	public static void recyledBitmap() {
		try {
			int i = 0;
			if (imageCache != null && !imageCache.isEmpty()) {
				Set<Map.Entry<String, SoftReference<Bitmap>>> entrys = imageCache.entrySet();
				Iterator<Map.Entry<String, SoftReference<Bitmap>>> it1 = entrys.iterator();
				while (it1.hasNext()) {
					Map.Entry<String, SoftReference<Bitmap>> me = it1.next();
					String url = me.getKey();
					SoftReference<Bitmap> sr = me.getValue();
					Bitmap b = sr.get();
					if (b != null && !b.isRecycled()) {
						b.recycle();
						b = null;
					}
					it1.remove();
				}
				imageCache.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


	public InputStream getGifFile(Context context, String imageUrl) {
		FileInputStream in = null;
		try {
			cacheFile = getimgFilePath(context);
			String fileName = getFilePath(imageUrl);
			File file = new File(cacheFile, fileName);// 保存文件
			in = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}
	
	
    private static String PATH = "/Android/data/";
    

	public static File getimgFilePath(Context context){
		File cacheFile = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheFile = new File(Environment.getExternalStorageDirectory().getPath() + PATH +  "/" + "imgch");
			if (!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			cacheFile = context.getCacheDir();
		}
		return cacheFile;
	}
}
