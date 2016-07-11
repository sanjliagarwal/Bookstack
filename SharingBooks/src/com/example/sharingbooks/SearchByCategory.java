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
import android.app.AlertDialog;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchByCategory extends Activity implements OnClickListener,OnItemSelectedListener,OnItemClickListener{
	ArrayList<String> list=new ArrayList<String>();
	EditText name;
	ImageButton searchbutt;
	ListView lv;
	CustomAdapter ca;
	Spinner sp;
	String catselect;
	TextView tvname,tvspinner;
	JSONParser jsonParser = new JSONParser();
	private static String url_create_product = "http://192.168.43.147/sbphp/checkingbycategory.php";
	private static final String TAG_SUCCESS = "success";
	private String tag = "CLOCKS";
	ArrayList<TwoStrings> detail = new ArrayList<TwoStrings>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchbycategory);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		sp=(Spinner) findViewById(R.id.catspinner);
		name=(EditText) findViewById(R.id.catname);
		searchbutt=(ImageButton) findViewById(R.id.catsearch);
		lv=(ListView) findViewById(R.id.catlv);
		tvspinner=(TextView) findViewById(R.id.cattvspinner);
		tvname=(TextView) findViewById(R.id.cattvname);
		list.add("Choose the category");
		list.add("Fiction");
		list.add("Computer Engineering");
		list.add("Civil Engineering");
		list.add("Mechanical Engineering");
		list.add("Electrical Engineering");
		list.add("Electronics Engineering");
		list.add("Mathematics");
		list.add("Physics");
		list.add("Chemistry");
		list.add("English");
		list.add("Fitness");
		list.add("Others");
		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		sp.setAdapter(dataAdapter);
		sp.setOnItemSelectedListener(this);
		searchbutt.setOnClickListener(this);
		lv.setOnItemClickListener(this);
	}
	public void createdialog(View v)
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String na;
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setPositiveButton("Ok", null);
		ad.setCancelable(false);
		ad.setTitle("Alert!");
		ad.setMessage("Type the name of books you are searching");
		AlertDialog message = ad.create();
		na=name.getText().toString();
		Log.i("CLOCKS2","Value of name and category obtained"+na+catselect);
		int checkna=1,checkcat=1;
		//checkna=checkname(na);
		//checkcat=checkcategory(catselect);
		/*if (na==null || na.length()==0)
		{
			checkna=0;
			message.show();
		}*/
		if (checkna==1 && checkcat==1)
		{
			Log.i("CLOCKS2","both values checked and 1 is verified");
			detail.clear();
			new searchbycategoryjson().execute();
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		// TODO Auto-generated method stub
		if (position != 0) {
			String item = parent.getItemAtPosition(position).toString();
			Log.i("CLOCKS", "item is obtained");
			// Showing selected spinner item
			catselect=item;
			Toast.makeText(parent.getContext(), "Selected: " + item,
					Toast.LENGTH_LONG).show();
			tvspinner.setText("");
		} else {
			tvspinner.setText("Please choose the category");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
class searchbycategoryjson extends AsyncTask<String, String, String> {
		int vv;
		protected String doInBackground(String... args) {
			String sname = name.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("category", catselect));
			params.add(new BasicNameValuePair("name", sname));
			// getting JSON Object
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
							dd = dd + "\nPossessed by: " + obname;
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
						Log.i("CLOCKS","Searched element is not");
						
						//createdialog(SearchByCategory.this);
						Log.i("CLOCKS","Toast is toasted");
						Intent myintent = new Intent(SearchByCategory.this, SearchByCategory.class);
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
			if (vv==1){
			Log.i("CLOCKS", "Listview set");
			ca=new CustomAdapter(SearchByCategory.this,R.layout.itemlist2string,detail);
			Log.i("CLOCKS", "Array adapter set");
			lv.setAdapter(ca);
			Log.i("CLOCKS",lv.toString());
			//detail.clear();
			Log.i("CLOCKS", "Content set");
			}
			else
			{
				Toast tt=Toast.makeText(SearchByCategory.this, "Element not found",
						Toast.LENGTH_LONG);
				tt.show();
			}
		}
	}
@Override
public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
			TextView tv=(TextView) v.findViewById(R.id.down);
			Log.i("CLOCKS2","Textview value"+tv);
			TextView tv2=(TextView) v.findViewById(R.id.up);
			Log.i("CLOCKS2","second value"+tv2);
			String maildata=tv.getText().toString();
			String bookdata=tv2.getText().toString();
			Intent i=new Intent(SearchByCategory.this,DisplayDetails.class);
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
