package ufrj.sps.electronicqueueclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

/**
 *Creates a new controller. The user needs to be logged in and have owner privileges
 */

public class CreateControllerActivity extends Activity {

	private final String mUrlCreateController = "http://" + TheIP  + Port + "/axis2/services/EQCloud/createController?login=";

	private Context mContext = this;

	private EditText mLoginField, mPasswordField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_controller);
		
		mLoginField = (EditText) findViewById(R.id.editText2);
		mPasswordField = (EditText) findViewById(R.id.editText1);
		
		Button loginButton = (Button) findViewById(R.id.Button01);
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Cache cache = (Cache) getApplicationContext();
				
				String controllerLogin = mLoginField.getText().toString();
				String controllerPassword = mPasswordField.getText().toString();
				
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(mUrlCreateController + controllerLogin + "&password=" +
                        controllerPassword + "&owner=" + Integer.toString(cache.getUserID()));
				String answer = httpRequester.getResponse();
				
				if(answer.equals("true")){
					
					Toast.makeText(mContext, "The controller " + controllerLogin +
                            " has been created!", Toast.LENGTH_SHORT).show();
					finish();
					
				}
				else{
					
					Toast.makeText(mContext, "A problem has occurred. Please, try again.",
                            Toast.LENGTH_SHORT).show();
					
				}
			}
					
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_controller, menu);
		return true;

	}

}
