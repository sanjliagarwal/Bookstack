package com.example.sharingbooks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewBooks extends Activity {

	String mailofuser;
	JSONParser jsonParser = new JSONParser();
	private static String url_create_product = "http://192.168.43.147/sbphp/viewing_books.php";
	private static final String TAG_SUCCESS = "success";
	private String tag = "CLOCKS";
	ListView lv;
	ArrayAdapter<String> mArrayAdapter;
	ArrayList<String> detail=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stu
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewbooks);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		Log.i("CLOCKS","HI");
		Bundle bb = getIntent().getExtras();
		mailofuser = bb.getString("mailid");
		lv=(ListView) findViewById(R.id.lv);
		mArrayAdapter = new ArrayAdapter<String>(this,R.layout.listitem, detail);
		new viewing().execute();
		
		
		Log.i("CLOCKS","Content set");
		
		Log.i("CLOCKS","email extracted in viewing");
		
	}

	class viewing extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {
			String smail = mailofuser;

			// final Context context=this;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mail", smail));
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			int i, l;
			String obname, obauthor, obedition, obcategory;
			Log.d("Create Response", (json.toString()));

			Log.i(tag, "json View" + json.toString());

			// check for success tag
			try {
				if (json.has("success")) {
					Log.i("CLOCKS", "success tag is there View");
				}
				int success;
				success = json.getInt("success");
				Log.i("CLOCKS", "Value of success View" + success);
				if (success == 1) {
					// found sn emsil
					/*
					 * AlertDialog.Builder pd=new
					 * AlertDialog.Builder(LoginActivity.this); pd.create();
					 * pd.setMessage("Welcome "+name); pd.setCancelable(false);
					 * pd.setPositiveButton("Ok", null); pd.show();
					 */
					try {
						JSONArray bookarray=json.getJSONArray("book");
						 l=bookarray.length();
						 for(i=0;i<l;i++)
						 {
							 JSONObject ob=bookarray.getJSONObject(i);
							 obname=ob.getString("Name");
							 obauthor=ob.getString("Author");
							 obedition=ob.getString("Edition");
							 
							 obcategory=ob.getString("Category");
							 String dd="Name: "+obname
									 +"\nWritten By: "+obauthor;
							 if (!(obedition==null || obedition.contentEquals("")))
							 {
								 dd=dd+"\nEdition: "+obedition;
							 }
							 dd=dd+"\nCategory is: "+obcategory;
							 detail.add(dd);
							 Log.i("CLOCKS","Details after each for loop"+detail);
							 Log.i("CLOCKS","deatil:"+dd);
							 
						 }
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Log.i("CLOCKS", "Just before gthe intent statemnet");
					/*Intent i = new Intent(ViewBooks.this, Mainscreen.class);
					i.putExtra("nameofuser", namepp);
					i.putExtra("mailid", mailid);
					Log.i("CLOCKS", "Intent staemment activated");
					if (i != null) {
						Log.i("CLOCKS", "Intent is not null" + i.toString());
					} else {
						Log.e("CLOCKS", "error in intent" + i.toString());
					}
					startActivity(i);
					Log.i("CLOCKS", "activity started");

					// closing this screen
					finish();*/
				} else {
					/*
					 * AlertDialog.Builder pd=new
					 * AlertDialog.Builder(LoginActivity.this); pd.create();
					 * pd.setMessage("Invalid details ");
					 * pd.setCancelable(false); pd.setPositiveButton("Ok",
					 * null); pd.show();
					 */
					try {
						Class<?> myclass = Class
								.forName("com.example.sharingbooks.LoginActivity");
						Intent myintent = new Intent(ViewBooks.this, myclass);
						startActivity(myintent);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					// closing this screen
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			lv.setAdapter(mArrayAdapter);
		}
	}
}
