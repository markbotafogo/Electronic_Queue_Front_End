package ufrj.sps.electronicqueueclient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

/**
 * Provides the options for the user logged in with owner privileges. The user can create new queues
 * and new controllers and also can live watch your queues.
 */

public class OwnerActivity extends Activity {

    private String mUrlSearchQueueByOwner = "http://" + TheIP  + Port + "/axis2/services/EQCloud/searchQueueOwnerID?ownerId=";

    private final Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner);
		
		Cache cache = (Cache) getApplicationContext();
		
		TextView ownerName = (TextView) findViewById(R.id.TextView01);
		ownerName.setText("Welcome " + cache.getUserName());
		
		Log.i("Debug","Teste 1");
		Button observerActivityButton = (Button) findViewById(R.id.button1);
		observerActivityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Cache cache = (Cache) getApplicationContext();
				
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(mUrlSearchQueueByOwner + Integer.toString(cache.getUserID()));
				ArrayList<String> list = httpRequester.getResponses();
				
				Intent intent = new Intent(OwnerActivity.this, ObserverActivity.class);
				intent.putStringArrayListExtra("list", list);
				startActivity(intent);
				
			}			
		});
		
		Log.i("Debug","Teste 2");
		Button createQueueButton = (Button) findViewById(R.id.button2);
		createQueueButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(OwnerActivity.this, CreateQueueActivity.class);
				startActivity(intent);
				
			}
		});

		Log.i("Debug","Teste 3");
		Button createControllerButton = (Button) findViewById(R.id.button3);
		createControllerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					
				Intent intent = new Intent(OwnerActivity.this, CreateControllerActivity.class);
				startActivity(intent);			
								
			}
		});
		
		Log.i("Debug","Teste 4");
		Button logoutButton = (Button) findViewById(R.id.button4);
		logoutButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Cache cache = (Cache) getApplicationContext();
				cache.logout();
				finish();

			}
		});

		Log.i("Debug","Teste 5");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.owner, menu);
		return true;

	}

}
