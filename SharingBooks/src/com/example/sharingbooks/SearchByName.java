package com.example.sharingbooks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sharingbooks.ViewBooks.viewing;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchByName extends Activity implements OnClickListener,OnItemClickListener {
	EditText name;
	ImageButton searchbutt;
	ListView lv;
	ProgressDialog pDialog;
	Context c;
	//ArrayAdapter<TwoString> mArrayAdapter ;
	CustomAdapter ca;
	JSONParser jsonParser = new JSONParser();
	private static String url_create_product = "http://192.168.43.147/sbphp/checkingbyname.php";
	private static final String TAG_SUCCESS = "success";
	private String tag = "CLOCKS";
	ArrayList<TwoStrings> detail = new ArrayList<TwoStrings>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchbyname);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		name = (EditText) findViewById(R.id.searchname);
		searchbutt = (ImageButton) findViewById(R.id.searchbutton);
		lv = (ListView) findViewById(R.id.searchlv);
		Log.i("CLOCKS", "List view value" + lv.toString());
		searchbutt.setOnClickListener(this);
		Log.i("CLOCKS2","Alert is sccessfully created");
		lv.setOnItemClickListener(this);
	}
	public void createdialog()
	{
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setPositiveButton("Ok", null);
		ad.setCancelable(false);
		ad.setTitle("Alert!");
		ad.setMessage("Type the name you want to search");
		AlertDialog message = ad.create();
		message.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setPositiveButton("Ok", null);
		ad.setCancelable(false);
		ad.setTitle("Alert!");
		ad.setMessage("Type the name you want to search");
		AlertDialog message = ad.create();
		String nametext;
		nametext = name.getText().toString();
		if ((nametext == null) || (nametext.length() == 0)) {
			message.show();
		} else {
			detail.clear();
			new searchbynamejson().execute();
			Log.i("CLOCKS", "Json is properly executed");

		}
	}

	class searchbynamejson extends AsyncTask<String, String, String> {
		int vv;
		protected String doInBackground(String... args) {
			String sname = name.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", sname));
			// getting JSON Obj	ect
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			int i, l;
			String obnameofbook, obauthor, obedition, obcategory, obname, obmail, obcny, obphone;
			Log.d("Create Response", (json.toString()));
			int obid;
			Log.i(tag, "json View" + json.toString());

			// check for success tag
			try {
				if (json.has("success")) {
					Log.i("CLOCKS", "success tag is there View");
				}
				int success;
				
				success = json.getInt("success");
				vv=success;
				Log.i("CLOCKS", "Value of success View" + success);
				if (success == 1) {
					try {
						JSONArray bookarray = json.getJSONArray("book");
						l = bookarray.length();
						for (i = 0; i < l; i++) {
							JSONObject ob = bookarray.getJSONObject(i);
							obname = ob.getString("Name");
							obauthor = ob.getString("Author");
							obedition = ob.getString("Edition");
							obnameofbook = ob.getString("Nameofbook");
							obmail = ob.getString("Email");
							obcny = ob.getString("Cny");
							obphone = ob.getString("Phone");
							obcategory = ob.getString("Category");
							obid=ob.getInt("Id");
							String dd = obnameofbook + "\nWritten By: "
									+ obauthor;
							if (!(obedition == null || obedition
									.contentEquals(""))) {
								dd = dd + "\nEdition: " + obedition;
							}
							dd = dd + "\nCategory is: " + obcategory;
							TwoStrings ts=new TwoStrings(dd,obmail);
							detail.add(ts);
							Log.i("CLOCKS", "Details after each for loop"
									+ detail);
							Log.i("CLOCKS", "deatil:" + dd);

						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Log.i("CLOCKS", "List is formed");
				} else {
						Log.i("CLOCKS","Searched element is not there");
						//Toast tt=Toast.makeText(SearchByName.getApplicationContext(), "Element not found",
								//Toast.LENGTH_LONG);
						//tt.show();
						Log.i("CLOCKS","Toast is toasted");
						Intent myintent = new Intent(SearchByName.this, SearchByName.class);
						startActivity(myintent);
						name.requestFocus();

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
			Log.i("CLOCKS", "Listview set");
			if(vv==1){
			ca=new CustomAdapter(SearchByName.this,R.layout.itemlist2string,detail);
			
			lv.setAdapter(ca);
			Log.i("CLOCKS", "Array adapter set");
			
			Log.i("CLOCKS",lv.toString());
			//detail.clear();
			Log.i("CLOCKS", "Content set");
			}
			else{
				Toast tt=Toast.makeText(SearchByName.this, "Element not found",
						Toast.LENGTH_LONG);
				tt.show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub
		TextView tv=(TextView) v.findViewById(R.id.down);
		Log.i("CLOCKS2","Textview value"+tv);
		TextView tv2=(TextView) v.findViewById(R.id.up);
		Log.i("CLOCKS2","second value"+tv2);
		String maildata=tv.getText().toString();
		String bookdata=tv2.getText().toString();
		Intent i=new Intent(SearchByName.this,DisplayDetails.class);
		if (i!=null)
		{
			Log.i("CLOCKS2","value of intent for the display"+(i.toString()));
		}
		i.putExtra("mail", maildata);
		Log.i("CLOCKS2","value of mail parsed"+maildata);
		i.putExtra("book",bookdata);
		Log.i("CLOCKS2","value of book parsed"+bookdata);
		startActivity(i);
		Log.i("CLOCKS2","activity od displaying started");
		
	}

}
