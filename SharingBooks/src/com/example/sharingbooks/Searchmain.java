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

public class Searchmain extends Activity implements OnClickListener{
	Button byname,byauthor,bycategory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchmain);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		Log.i("CLOCKS","search main started");
		byname=(Button) findViewById(R.id.sbname);
		byauthor=(Button) findViewById(R.id.sbauthor);
		bycategory=(Button) findViewById(R.id.sbcategory);
		byname.setOnClickListener(this);
		byauthor.setOnClickListener(this);
		bycategory.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		Log.i("CLOCKS","Id is retrived");
		Intent i;
		switch (id)
		{
		case R.id.sbname:
			i=new Intent(Searchmain.this,SearchByName.class);
			Log.i("CLOCKS","Intent is created"+i.toString());
			startActivity(i);
			Log.i("CLOCKS"," Search by name Activity is started");
			break;
		case R.id.sbauthor:
			i=new Intent(Searchmain.this,SearchByAuthor.class);
			Log.i("CLOCKS","Intent is created");
			startActivity(i);
			Log.i("CLOCKS"," Search by author Activity is started");
			break;
		case R.id.sbcategory:
			i=new Intent(Searchmain.this,SearchByCategory.class);
			Log.i("CLOCKS","Intent is created");
			startActivity(i);
			Log.i("CLOCKS"," Search by category Activity is started");
			break;
		}
	}
	
}
