package com.example.sharingbooks;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>, OnClickListener {

	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };
	JSONParser jsonParser=new JSONParser();
	private static String url_create_product="http://192.168.43.147/sbphp/check_user.php";
	 private static final String TAG_SUCCESS="success";
	 private String tag="CLOCKS";
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	private TextView tvemail;
	private TextView tvpass;
	private Button msignin,mregister;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		//Spannable text = new SpannableString(bar.getTitle());
		//text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		//bar.setTitle(text);
		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);
		//populateAutoComplete();
		mPasswordView = (EditText) findViewById(R.id.password);
		/*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});*/

		msignin=(Button) findViewById(R.id.email_sign_in_button);
		mregister=(Button) findViewById(R.id.register_button);
		tvemail=(TextView) findViewById(R.id.tvmail);
		tvpass=(TextView) findViewById(R.id.tvpassword);
		msignin.setOnClickListener(this);
		mregister.setOnClickListener(this);
		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch(id)
		{
		case R.id.email_sign_in_button:
			int v1,v2;
			v1=checkmail(mEmailView.getText().toString());
			v2=checkpassword(mPasswordView.getText().toString());
			if (v1==1 && v2==1)
			{
				new checkmail().execute();
			}
			else
			{
				if (v1==0)
				{
					tvemail.setText("Invalid Email Address");
				}
				else
					tvemail.setText("");
				if (v2==0)
				{
					tvpass.setText("invalid password");
				}
				else
					tvpass.setText("");
			}
			break;
		case R.id.register_button:
			try{
				Class<?> myclass= Class.forName("com.example.sharingbooks.RegisterActivity");
				Intent myintent = new Intent(LoginActivity.this, myclass);
				startActivity(myintent);
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
			break;
		}
	}
	public int checkpassword(String v)
	{
		int result=1,l;
		l=v.length();
		if (l>=4 && (l<=10))
		{
			result=1;
		}
		else
			result=0;
		return result;
	}
	public int checkmail(String v)
	{
		int result;
			   boolean a=android.util.Patterns.EMAIL_ADDRESS.matcher(v).matches();
			   if (a==true)
				   result=1;
			   else
				   result=0;
		return result;
	}
	
	class checkmail extends AsyncTask<String,String,String>{
		
		int vv;
		protected String doInBackground(String... args) {
            String smail = mEmailView.getText().toString();
            String spass = mPasswordView.getText().toString();
            //final Context context=this;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mail", smail));
            params.add(new BasicNameValuePair("password", spass));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", (json.toString()));
          
			Log.i(tag,"json"+json.toString());
 
            // check for success tag
            try {
            	if(json.has("success"))
            	{
            		Log.i("CLOCKS","success tag is there");
            	}
            	int success=1;
                success = json.getInt("success");
                vv=success;
                Log.i("CLOCKS","Value of success"+success);               
                String namepp;
                namepp= json.getString("Name");
                if (namepp==null || namepp=="")
                {
                	Log.i("CLOCKS","string obtained is null");
                }
                 Log.i("CL0CKS","value of name"+namepp);
                 String mailid=json.getString("Email");
                 Log.i("CLOCKS","value of mail id"+mailid);
                if (success == 1) {
                	/*SharedPreferences pref = SaveSharedPreferences.getSharedPreferences("MyPref"); // 0 - for private mode
                	Editor editor = pref.edit();
                	editor.putString("mailid",smail );
                	editor.putString("name", namepp);
                	editor.commit();*/
                	Log.i("CLOCKS","Just before gthe intent statemnet");	
                	Intent i = new Intent(LoginActivity.this, Mainscreen.class);
                	i.putExtra("nameofuser",namepp);
                	i.putExtra("mailid", mailid);
                		Log.i("CLOCKS","Intent staemment activated");
                		if (i!=null)
                		{
                			Log.i("CLOCKS","Intent is not null"+i.toString());
                		}
                		else
                		{
                			Log.e("CLOCKS","error in intent"+i.toString());
                		}
                        startActivity(i);
                        Log.i("CLOCKS","activity started");
 
                    // closing this screen
                    finish();
                } else {
                	try{
                    	Class<?> myclass= Class.forName("com.example.sharingbooks.LoginActivity");
    					Intent myintent = new Intent(LoginActivity.this, myclass);
    					startActivity(myintent);
                    	}catch(ClassNotFoundException e){
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
        	if (vv==0)
        	{
        		Toast tt=Toast.makeText(LoginActivity.this, "Unable to log in",
						Toast.LENGTH_LONG);
				tt.show();
        	}
            }
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		//addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
		}

		/*@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}*/
	}
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}
		
		@Override
		protected void onCancelled() {
			showProgress(false);
		}
	}

	
}
