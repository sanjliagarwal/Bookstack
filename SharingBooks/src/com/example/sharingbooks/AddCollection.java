package com.example.sharingbooks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddCollection extends Activity implements OnItemSelectedListener,
		OnClickListener {

	EditText name, author, edition;
	TextView tvname, tvauthor, tvedition, tvcategory;
	Spinner category;
	String mailofuser, cat, scategory;
	Button submit;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static String url_create_product = "http://192.168.43.147/sbphp/inserting_book.php";
	private static final String TAG_SUCCESS = "success";
	private String tag = "CLOCKS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcollection);
		ActionBar bar=getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		Bundle bb = getIntent().getExtras();
		mailofuser = bb.getString("mailid");
		name = (EditText) findViewById(R.id.a_etname);
		author = (EditText) findViewById(R.id.a_etauthor);
		edition = (EditText) findViewById(R.id.a_etedition);
		category = (Spinner) findViewById(R.id.a_spcategory);
		tvname = (TextView) findViewById(R.id.a_tvname);
		tvauthor = (TextView) findViewById(R.id.a_tvauthor);
		tvedition = (TextView) findViewById(R.id.a_tvedition);
		tvcategory = (TextView) findViewById(R.id.a_tvcategory);
		submit = (Button) findViewById(R.id.a_submit);
		List<String> list = new ArrayList<String>();
		// category.setPrompt("Pick one");
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
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		category.setAdapter(dataAdapter);
		category.setOnItemSelectedListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		// TODO Auto-generated method stub
		// On selecting a spinner item
		if (position != 0) {
			String item = parent.getItemAtPosition(position).toString();
			Log.i("CLOCKS", "item is obtained");
			cat = item;
			// Showing selected spinner item
			Toast.makeText(parent.getContext(), "Selected: " + item,
					Toast.LENGTH_LONG).show();
			tvcategory.setText("");
		} else {
			tvcategory.setText("Please choose the category");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		tvcategory.setText("Category wrong");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.a_submit) {
			int chname, chauthor, chcategory;
			chname = checkname(name.getText().toString());
			chauthor = checkauthor(author.getText().toString());
			chcategory = checkcategory(category.getSelectedItem().toString());
			scategory = category.getSelectedItem().toString();
			;
			if (chname == 1 && chauthor == 1 && chcategory == 1) {
				new insertbook().execute();
			}
		}
	}

	public int checkname(String na) {
		int ans, l;
		l = na.length();
		if (l == 0) {
			tvname.setText("Name of the book is missing");
			ans = 0;
		} else if (l > 30) {
			tvname.setText("Name is too long");
			ans = 0;
		} else {
			tvname.setText("");
			ans = 1;
		}
		return ans;
	}

	public int checkauthor(String au) {
		int ans, l;
		l = au.length();
		if (l == 0) {
			tvauthor.setText("Author of the book is missing");
			ans = 0;
		} else if (l > 30) {
			tvauthor.setText("Name of author is too long");
			ans = 0;
		} else {
			tvauthor.setText("");
			ans = 1;
		}
		return ans;
	}

	public int checkcategory(String ca) {
		int ans, l;
		Log.i("CLOCKS", "Category NAME" + ca);
		// l=ca.length();
		if (ca == null || ca.contentEquals("Choose the category")) {
			tvcategory.setText("category of the book is missing");
			ans = 0;
		} else {
			tvcategory.setText("");
			ans = 1;
		}
		return ans;
	}

	class insertbook extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		AlertDialog.Builder dd = new AlertDialog.Builder(AddCollection.this);
		int vv;
		// AlertDialog ad=dd.create();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddCollection.this);
			pDialog.setMessage("Adding to Collection");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			Log.i(tag, "onpreexecute done");
			dd.setPositiveButton("OK", null);
			dd.setCancelable(false);
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String sname = name.getText().toString();
			String sauthor = author.getText().toString();
			String sedition = edition.getText().toString();
			String smail = mailofuser;

			Log.i(tag, "values in string obtained");
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nameofbook", sname));
			params.add(new BasicNameValuePair("author", sauthor));
			params.add(new BasicNameValuePair("edition", sedition));
			params.add(new BasicNameValuePair("email", smail));
			params.add(new BasicNameValuePair("category", scategory));
			Log.i(tag, "values parsed");
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			if (json.has(TAG_SUCCESS)) {
				Log.i(tag, "json not empty" + json.toString());
			} else {
				Log.i(tag, json.toString());
			}
			// check log cat fro response
			Log.i(tag, "json" + json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				vv=success;
				if (success == 1 || success == 0) {
					Log.i("CLOCKS", "get int working fine");
				}

				if (success == 1) {
					Log.i("CLOCKS","Intent block entered");
					Intent myintent = new Intent(AddCollection.this,
							AddCollection.class);
					myintent.putExtra("mailid", mailofuser);
					Log.i("CLOCKS","Intent value"+myintent.toString());
					startActivity(myintent);
					Log.i("CLOCKS","ACTIVITY STARTED BY INTENT");
					finish();
				} else {
					// failed to create product
					// Toast.makeText(AddCollection.this,
					// "Error in book injection ", Toast.LENGTH_LONG).show();

					Intent myintent = new Intent(AddCollection.this,
							AddCollection.class);
					startActivity(myintent);

					finish();
				}
			} catch (JSONException e) {
				Log.e("CLOCKS", "exception in producing the dialog box");
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			if (vv==1){
			Toast tt=Toast.makeText(AddCollection.this, "Book added to the collection",
					Toast.LENGTH_LONG);
			tt.show();
			}
			else
			{
				Toast tt=Toast.makeText(AddCollection.this, "Unable to add the book",
						Toast.LENGTH_LONG);
				tt.show();
			}
			finish();
		}

	}
}
