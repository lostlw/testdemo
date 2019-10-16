package com.xhxm.ospot.constant;

import java.util.HashMap;
import java.util.Map;

public class AdStringZhConst {
	public static final String OPEN_KEY = "OPEN";
	public static final String CONFIRM = "CONFIRM";
	public static final String CANCEL = "CANCEL";
	Map<String, String> map = new HashMap<String, String>();

	public AdStringZhConst() {
		map.put(OPEN_KEY, "打开");
		map.put(CONFIRM, "确认");
		map.put(CANCEL, "取消");
	}

	public Map<String, String> getMap() {
		return map;
	}
}
