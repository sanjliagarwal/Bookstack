package com.example.sharingbooks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DisplayDetails extends Activity implements OnClickListener{
	String mail,book,pp;
	TextView tv1,tv2;
	Button mailb;
	JSONParser jsonParser=new JSONParser();
	private static String url_create_product="http://192.168.43.147/sbphp/detail_user.php";
	 private static final String TAG_SUCCESS="success";
	 private String tag="CLOCKS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaydetails);
		Bundle bb=getIntent().getExtras();
		Log.i("CLOCKS2","Extras obtained");
		mail=bb.getString("mail");
		Log.i("CLOCKS","mail value"+mail);
		book=bb.getString("book");
		Log.i("CLOCKS","Book data value"+book);
		tv1=(TextView) findViewById(R.id.bookdata);
		tv2=(TextView) findViewById(R.id.persondata);
		new checkmail().execute();
		mailb=(Button) findViewById(R.id.emailbutton);
		mailb.setOnClickListener(this);
	}
class checkmail extends AsyncTask<String,String,String>{
	
	
	protected String doInBackground(String... args) {
        String smail = mail;
       
        //final Context context=this;
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("mail", smail));
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
            Log.i("CLOCKS","Value of success"+success);               
            String namepp,phone,cny;
             String mailid=json.getString("Email");
             Log.i("CLOCKS","value of mail id"+mailid);
            if (success == 1) {
            	 namepp= json.getString("Name");
                 phone=json.getString("Phone");
                 Log.i("CLOCKS","phone value "+phone);
                 cny=json.getString("Cny");
                 Log.i("CLOCKS","cny value"+cny);
                // Log.i("CL0CKS2","value of name"+namepp);
                 if (namepp==null || namepp.contentEquals(""))
                 {
                 	Log.i("CLOCKS","string obtained is null");
                 }
                 pp=namepp+"\n"+mailid;
                 if (phone==null || phone.contentEquals(""))
                 {
                	 phone="";
                 }
                 else
                 {
                	 pp=pp+"\n"+phone;
                 }
                 if (cny==null || cny.contentEquals("")) 
                 {
                	 cny="";
                 }
                 else
                 {
                	 pp=pp+"\n"+cny;
                 }
                 Log.i("CLOCKS2","pura string"+pp);
                 
                // closing this screen
            } else {
            	/*AlertDialog.Builder pd=new AlertDialog.Builder(LoginActivity.this);
            	pd.create();
            	pd.setMessage("Invalid details ");
            	pd.setCancelable(false);
            	pd.setPositiveButton("Ok", null);
            	pd.show();*/
            	try{
                	Class<?> myclass= Class.forName("com.example.sharingbooks.SearchByName");
					Intent myintent = new Intent(DisplayDetails.this, myclass);
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
    	tv1.setText(book);
        Log.i("CLOCKS2","first tv set");
        tv2.setText(pp);
        Log.i("CLOCKS2","Second tv set");
        // dismiss the dialog once done
        }
}
@Override
public void onClick(View v) {
	int id;
	id=v.getId();
	String arrmail[]={mail};
	// TODO Auto-generated method stub
	if (id==(R.id.emailbutton)){
	String message="Dear,\nI would like to borrow this from you for some days!\n Kindly allow me to do this.";
	Intent emailintent=new Intent(android.content.Intent.ACTION_SEND);
	emailintent.putExtra(android.content.Intent.EXTRA_EMAIL, arrmail);
	emailintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Borrowing book via bookstack");
	emailintent.setType("plain/text");
	emailintent.putExtra(android.content.Intent.EXTRA_TEXT, message);
	startActivity(emailintent);
	}
}
}