package com.sevensevenlife.view.custumview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.example.youxiangshenghuo.R;


/**
 * 在style 配置文件中加入
 * 
 * <style name="FullHeightDialog"
	    parent="android:style/Theme.Dialog">
	      <item name="android:windowNoTitle">true</item>
	      
	      <item name="android:windowFrame">@null</item>
		  <item name="android:windowIsFloating">true</item>
		  <item name="android:windowContentOverlay">@null</item>
		  <!-- <item name="android:background">#FFF</item> -->
		  <item name="android:windowBackground">@drawable/touming</item>
  </style>
 * ***/
public class MyDialogView {

    private Dialog dialog;
	public MyDialogView(Context context) {
		dialog = new Dialog(context, R.style.dialog);
	}
	
	
	public void show(View v){
		dialog.setContentView(v);
		dialog.show();
	}
	
	public void dismiss(){
		dialog.dismiss();
	}
	
	public Dialog getDialog(){
		return dialog;
	} 
}
