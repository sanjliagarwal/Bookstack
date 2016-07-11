package com.example.sharingbooks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Mainscreen extends Activity implements OnClickListener{
ImageButton add,view,search;
TextView addbook,viewbook,searchbook,heading;
 String nameofuser;
String mailofuser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		Bundle bb=getIntent().getExtras();
		mailofuser=bb.getString("mailid");
		if (mailofuser!=null)
		{
		Log.i("CLOCKS","mail id recieved"+mailofuser);
		}
		nameofuser=bb.getString("nameofuser");
		addbook=(TextView) findViewById(R.id.m_addbook);
		viewbook=(TextView) findViewById(R.id.m_viewbook);
		searchbook=(TextView) findViewById(R.id.m_searchbook);
		heading=(TextView) findViewById(R.id.m_heading);
		add=(ImageButton) findViewById(R.id.m_button1);
		view=(ImageButton) findViewById(R.id.m_button2);
		search=(ImageButton) findViewById(R.id.m_button3);
		heading.setText("Welcome "+nameofuser);
		add.setOnClickListener(this);
		view.setOnClickListener(this);
		search.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		Intent i;
		switch(id)
		{
		case R.id.m_button1:
			i=new Intent(Mainscreen.this,AddCollection.class);
			i.putExtra("mailid", mailofuser);
			startActivity(i);
			break;
		case R.id.m_button2:
			i=new Intent(Mainscreen.this,ViewBooks.class);
			i.putExtra("mailid", mailofuser);
			startActivity(i);
			Log.i("CLOCKS","Activity of viwing books started");
			break;
		case R.id.m_button3:
			i=new Intent(Mainscreen.this,Searchmain.class);
			if (i!=null)
			{
				Log.i("CLOCKS","Intent of searchbook exits "+i.toString());
			}
			else
			{
				Log.i("CLOCKS","Intent has a problem"+i.toString());
			}
			startActivity(i);
			Log.i("CLOCKS","activity of search book started");
			break;
		}
	}
	

}
