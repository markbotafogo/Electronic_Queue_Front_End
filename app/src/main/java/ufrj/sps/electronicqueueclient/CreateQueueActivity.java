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
 *Creates a new queue. The user needs to be logged in and have owner privileges
 */

public class CreateQueueActivity extends Activity {

	private String mUrlCreateQueue = "http://" + TheIP  + Port + "/axis2/services/EQCloud/createQueue?name=";

	private Context mContext = this;

	private EditText mNameField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_queue);
		
		mNameField = (EditText) findViewById(R.id.editText2);
		
		Button createQueueButton = (Button) findViewById(R.id.button1);
		createQueueButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				String queueName = mNameField.getText().toString();
				
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(mUrlCreateQueue + queueName + "&owner=" +
                        Integer.toString(cache.getUserID()));
				String answer = httpRequester.getResponse();
				
				if(answer.equals("true")){
					
					Toast.makeText(mContext, "The queue " + queueName + " has been created!",
                            Toast.LENGTH_SHORT).show();
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
		getMenuInflater().inflate(R.menu.create_queue, menu);
		return true;

	}

}
