package com.example.myfitnessjourney.Controller;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends ActionBarActivity {
	EditText mEditEmail, mEditPassword;
	Button mLogin, mFacebookLogin, mGoogleLogin;
	TextView mSignUp;
	public static String LOGIN_ID = "login_id";
	String LOGIN_FAILURE_MESSAGE = "Invalid username or password, try again.";
	String loginid = "frest460";
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//Realm.deleteRealmFile(getApplicationContext());


		//Initialize widgets
		mEditEmail = (EditText) findViewById(R.id.email_text);
		mEditPassword = (EditText) findViewById(R.id.password_text);
		mLogin = (Button) findViewById(R.id.login_button);
		mFacebookLogin = (Button) findViewById(R.id.facebook_login);
		mGoogleLogin = (Button) findViewById(R.id.google_login);
		mSignUp = (TextView) findViewById(R.id.signup_text_button);

		//Check for active session before setting content view
		createListeners();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Create listeners for widgets
	public void createListeners() {
		
		mLogin.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ParseUser.logInInBackground(mEditEmail.getText().toString(), mEditPassword.getText().toString(), new LogInCallback() {

					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
						if (user != null) {
							Intent i = new Intent (getBaseContext(), HomeActivity.class);
							loginid = user.getUsername();
							i.putExtra(LOGIN_ID, loginid);
							startActivity(i);
							
						} else {
							Log.d("Login", "failed");
							
							AlertDialog.Builder adb = new AlertDialog.Builder(
								LoginActivity.this);
							
							adb.setTitle("Something went wrong");
							adb
							.setMessage(LOGIN_FAILURE_MESSAGE)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
							
							AlertDialog alertDialog = adb.create();
							 
							// show it
							alertDialog.show();
						}

					}

				});
				
					
						
			}
		});
		
		
		
	}
}
