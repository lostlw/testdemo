package com.xhxm.ospot.file;

import com.xhxm.ospot.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class Myshared {

	public static String getFileName(Context context) {
		String temp = Util.MD5fileName("shared" + context.getPackageName());
		return temp;
	}

	public static void saveData(Context context, String key, Object value) {

		if (value == null) {
			return;
		}
		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		SharedPreferences.Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, value.toString());
			// editor.putString(key, value.toString());
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, Boolean.getBoolean(value.toString()));
		} else if (value instanceof Float) {
			editor.putFloat(key, Float.parseFloat(value.toString()));
		} else if (value instanceof Integer) {
			editor.putInt(key, Integer.parseInt(value.toString()));
		} else if (value instanceof Long) {
			editor.putLong(key, Long.parseLong(value.toString()));
		} else {
			editor.putString(key, value.toString());
		}
		editor.commit();
	}

	public static void removeData(Context context, String key) {

		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		if (sp.contains(key))
			sp.edit().remove(key).commit();
	}

	public static String getString(Context context, String key,
			String defaultValue) {

		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		return sp.getString(key, defaultValue);
	}

	public static boolean getBoolean(Context context, String key,
			boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		return sp.getBoolean(key, defaultValue);
	}

	public static void setBoolean(Context context, String key,
			boolean defaultValue) {

		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, defaultValue);
		editor.commit();
	}

	public static float getFloat(Context context, String key, float defaultValue) {

		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		return sp.getFloat(key, defaultValue);
	}

	public static int getInt(Context context, String key, int defaultValue) {

		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		return sp.getInt(key, defaultValue);
	}

	public static long getLong(Context context, String key, long defaultValue) {

		SharedPreferences sp = context.getSharedPreferences(
				getFileName(context), 0);
		return sp.getLong(key, defaultValue);
	}

}
