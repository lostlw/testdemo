package com.xhxm.ospot;

import java.util.ArrayList;
import java.util.List;

import com.xhxm.ospot.model.AdContent;
/**
 * 广告池
 * @author 
 */
public class AdPool {

	
	private static List<AdContent> splashads = new ArrayList<AdContent>();
	private static List<AdContent> intersititialads = new ArrayList<AdContent>();
	
	
	public static AdContent getSplashAd(){
		if(!splashads.isEmpty()){
			return splashads.get(0);
		}
		return null;
	}
	
	
	public static void addSplashAd(AdContent mAdContent){
		splashads.add(0,mAdContent);
	}
	
	
	public static void addIntersititialad(AdContent mAdContent){
		intersititialads.add(0,mAdContent);
	}
	public static void setIntersititialad(List<AdContent> mintersititialads){
		intersititialads = mintersititialads;
	}
	
	public static AdContent getIntersititialAd(){
		if(!intersititialads.isEmpty()){
			return intersititialads.get(0);
		}
		return null;
	}
	
	public static List<AdContent> getIntersititialAds(){
		if(!intersititialads.isEmpty()){
			return intersititialads;
		}
		return null;
	}
	
	
	public static boolean ishavaexitads(){
		return !splashads.isEmpty();
	}
	
	
	public static void removExitads(){
		if(!splashads.isEmpty()){
			splashads.remove(0);
		}
	}
	
	public static void removIntersititialads(){
		
		if(!intersititialads.isEmpty()){
			intersititialads.clear();
		}
	}
	
	
	public static void removIntersititialads(int pos){
		if(intersititialads.size()>pos){
			intersititialads.remove(pos);
		}
	}
	
	public static boolean ishavaintersititialadsads(){
		return !intersititialads.isEmpty();
	}
	
	
	
}
