package com.xhxm.ospot.model;

import java.io.Serializable;

/***
 * 
* <p>Title: AdContent</p>
* <p>Description: 广告内容信息类</p>
 */
public class AdContent implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	
	/**apk包名*/
	private String pkg;
	
	/** 拉活链接 */
	private String deepLink;
	
	private String imgLink;
	
	private String imgDesc;
	
	private String show;
	private String click;
	
	
	private String iconurl;
	private String title;
	
	
	
	private String price = "";
	
	
	
	
	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getDeepLink() {
		return deepLink;
	}

	public void setDeepLink(String deepLink) {
		this.deepLink = deepLink;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getImgDesc() {
		return imgDesc;
	}

	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	private String jsoninfo;
	
	
	
	public String getJsoninfo() {
		return jsoninfo;
	}

	public void setJsoninfo(String jsoninfo) {
		this.jsoninfo = jsoninfo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	
}
