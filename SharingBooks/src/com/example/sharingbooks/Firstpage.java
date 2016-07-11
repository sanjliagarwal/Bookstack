package com.example.sharingbooks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.lang.Thread;


public class Firstpage extends Activity{

	@Override
	protected void onCreate(Bundle myinstance) {
		// TODO Auto-generated method stub
		super.onCreate(myinstance);
		setContentView(R.layout.firstpage);
		Thread timer=new Thread(){
			public void run(){
			try{
				sleep(3000);
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}finally{
				//String ab=pref.getString("mailid",null);
				Intent openmain=new Intent(Firstpage.this,LoginActivity.class);
				startActivity(openmain);
			}
			}
	};
	timer.start();

}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}
