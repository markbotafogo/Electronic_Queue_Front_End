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

import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

public class CreateControllerActivity extends Activity {

	EditText e1, e2;
	Button b1;
	String urlCreateController = "http://" + TheIP  + ":8080/axis2/services/EQCloud/createController?login=";	
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_controller);
		
		e1 = (EditText) findViewById(R.id.editText2);
		e2 = (EditText) findViewById(R.id.editText1);
		
		b1 = (Button) findViewById(R.id.Button01);
		b1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				String controllerName = e1.getText().toString();
				String controllerPassword = e2.getText().toString();
				
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(urlCreateController + controllerName + "&password=" + controllerPassword + "&owner=" + Integer.toString(cache.getUserID()));
				String answer = httpRequester.getResponse();
				
				if(answer.equals("true")){
					
					Toast.makeText(context, "The controller " + controllerName + " has been created!", Toast.LENGTH_SHORT).show();
					finish();
					
				}
				else{
					
					Toast.makeText(context, "A problem has occurred. Please, try again.", Toast.LENGTH_SHORT).show();
					
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
