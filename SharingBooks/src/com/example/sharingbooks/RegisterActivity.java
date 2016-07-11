package com.example.sharingbooks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText name,password,conpassword,mail,cny,phone;
	private TextView tvname,tvpass,tvconpass,tvmail,tvphone;
	private ProgressDialog pDialog;
	private Button reg;
	JSONParser jsonParser=new JSONParser();
	private static String url_create_product="http://192.168.43.147/sbphp/inserting_row.php";
	 private static final String TAG_SUCCESS="success";
	 private String tag="CLOCKS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registeractivity);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		setvalues();
	}
	public void setvalues()
	{
		name=(EditText) findViewById(R.id.r_name);
		password=(EditText) findViewById(R.id.r_password);
		conpassword=(EditText) findViewById(R.id.r_confirm_password);
		mail=(EditText) findViewById(R.id.r_email);
		cny=(EditText) findViewById(R.id.r_courseandyear);
		phone=(EditText) findViewById(R.id.r_phone);
		reg=(Button) findViewById(R.id.r_registerbutton);
		tvname=(TextView) findViewById(R.id.r_tvname);
		tvpass=(TextView) findViewById(R.id.r_tvpassword);
		tvconpass=(TextView) findViewById(R.id.r_tvconfirm_password);
		tvmail=(TextView) findViewById(R.id.r_tvemail);
		tvphone=(TextView) findViewById(R.id.r_tvphone);
		reg.setOnClickListener(this);
	}
	public int checkname(String v)
	{
		int result=1,l,i;
		l=v.length();
		for (i=0;i<l;i++)
		{
			if (!(Character.isAlphabetic(v.charAt(i))))
			{
				result=0;
				break;
			}
		}
		return result;
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
	public int checkconpass(String u,String v)
	{
		int result;
		if (checkpassword(u)==1)
		{
			if (u.compareTo(v)==0)
				result=1;
			else
				result=0;
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
	public int checkphone(String v)
	{
		int result=1;
		int i,l;
		l=v.length();
		if (!(l==10 || (v.isEmpty())))
		{
			result=0;
		}
		else
		{
			for (i=0;i<l;i++)
			{
				if (!(Character.isDigit(v.charAt(i))))
				{
					result=0;
					break;
				}
			}
		}
		return result;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		String errname,errpass,errconpass,errmail,errphone,ans="";
		errname="This is not a valid name";
		errpass="password should be between 4 to 10 chars";
		errconpass="invalid confirmation";
		errmail="invalid e-mail address";
		errphone="invalid phone number";
		int i_name,i_pass,i_conpass,i_mail,i_phone;
		if (id==R.id.r_registerbutton)
		{
			i_name=checkname(name.getText().toString());
			i_pass=checkpassword(password.getText().toString());
			i_conpass=checkconpass(password.getText().toString(),conpassword.getText().toString());
			i_mail=checkmail(mail.getText().toString());
			i_phone=checkphone(phone.getText().toString());
			if (i_name==1 && i_pass==1 && i_conpass==1 && i_mail==1 && i_phone==1)
			{
				
			new CreateNewProduct().execute();
				/*try{
					Class<?> myclass= Class.forName("com.example.sharingbooks.Mainscreen");
					Intent myintent = new Intent(RegisterActivity.this, myclass);
					startActivity(myintent);
					}catch (ClassNotFoundException e){
						e.printStackTrace();
					}*/
			}
			else
			{
				if (i_name==0)
				{
					if (name.toString()=="")
						ans="name is compulsary";
					else
					ans=errname;
					name.requestFocus();
				}
				else
					ans="";
				tvname.setText(ans);
				
				if (i_pass==0)
				{
					if (password.toString()=="")
						ans="Password is compulsary";
					else
						ans=errpass;
					password.requestFocus();
				}
				else
					ans="";
				tvpass.setText(ans);
				if (i_conpass==0)
				{
					ans=errconpass;
					tvconpass.setText(ans);
				}
				if (i_mail==0)
				{
					if (mail.toString()=="")
					{
						ans="Mail id is compulsary";
					}
					else
						ans=errmail;
					
					mail.requestFocus();
				}
				else
					ans="";
				tvmail.setText(ans);
				if (i_phone==0)
				{
					ans=errphone;
					phone.requestFocus();
				}
				else 
					ans="";
				tvphone.setText(ans);
				
				
			}
		}
	}
	class CreateNewProduct extends AsyncTask<String, String, String>{
		 int vv;
        /**
         * Before starting background thread Show Progress Dialog
         * */
		AlertDialog.Builder dd=new AlertDialog.Builder(RegisterActivity.this);
		
		//AlertDialog ad=dd.create();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Registering User");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.i(tag,"onpreexecute done");
            dd.setPositiveButton("OK", null);
        	dd.setCancelable(false);
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String sname = name.getText().toString();
            String spass = password.getText().toString();
            String sconpass = conpassword.getText().toString();
            String smail=mail.getText().toString();
            String scny=cny.getText().toString();
            String sphone=phone.getText().toString();
            Log.i(tag,"values in string obtained");
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", sname));
            params.add(new BasicNameValuePair("password", spass));
            params.add(new BasicNameValuePair("conpassword", sconpass));
            params.add(new BasicNameValuePair("email",smail));
            params.add(new BasicNameValuePair("cny", scny));
            params.add(new BasicNameValuePair("phone", sphone));
            Log.i(tag,"values parsed");
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            if (json.has(TAG_SUCCESS))
            {
            	Log.i(tag,"json not empty"+json.toString());
            }
            else
            {
            	Log.i(tag,json.toString());
            }
            // check log cat fro response         
			Log.i(tag,"json"+json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                vv=success;
                if (success==1 || success==0)
                {
                	Log.i("CLOCKS","get int working fine");
                }
               
                if (success == 1) {
                   
                    finish();
                	try{
    					Class<?> myclass= Class.forName("com.example.sharingbooks.LoginActivity");
    					Intent myintent = new Intent(RegisterActivity.this, myclass);
    					startActivity(myintent);
    					}catch (ClassNotFoundException e){
    						e.printStackTrace();
    					}
                } else {
                    
                	try{
    					Class<?> myclass= Class.forName("com.example.sharingbooks.RegisterActivity");
    					Intent myintent = new Intent(RegisterActivity.this, myclass);
    					startActivity(myintent);
    					}catch (ClassNotFoundException e){
    						e.printStackTrace();
    					}
                    finish();
                }
            } catch (JSONException e) {
            	Log.e("CLOCKS","exception in producing the dialog box");
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
           // dd.show();
            if (vv==1)
            {
            	Toast tt=Toast.makeText(RegisterActivity.this, "Registered Successfully",
						Toast.LENGTH_LONG);
				tt.show();
            }
            else
            {
            	Toast tt=Toast.makeText(RegisterActivity.this, "Problem in registering the user",
						Toast.LENGTH_LONG);
				tt.show();
            }
            finish();
            }
 
    }
}

