package com.briup.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
/**
 *
 * @author Administrator
 *
 */
public class ActivityCollector {

	public static List<Activity> activities=new ArrayList<Activity>();
	
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}
	
	//Destroy all activities
	public static void finishAll(){
		for(Activity activity:activities){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
}
