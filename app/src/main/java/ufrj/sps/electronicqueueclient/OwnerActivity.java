package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;

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

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

public class OwnerActivity extends Activity {

	Button b1, b2, b3, b4;
	TextView t1;
	String urlSearchQueueByOwner = "http://" + TheIP  + Port + "/axis2/services/EQCloud/searchQueueOwnerID?ownerId=";
	
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner);
		
		Cache cache = (Cache) getApplicationContext();
		cache = cache.getInstance();
		
		t1 = (TextView) findViewById(R.id.TextView01);
		t1.setText("Welcome " + cache.getUserName());
		
		Log.i("Debug","Teste 1");
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(urlSearchQueueByOwner + Integer.toString(cache.getUserID()));
				ArrayList<String> list = httpRequester.getResponses();
				
				Intent intent = new Intent(OwnerActivity.this, ObserverActivity.class);
				intent.putStringArrayListExtra("list", list);
				startActivity(intent);
				
			}			
		});
		
		Log.i("Debug","Teste 2");
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(OwnerActivity.this, CreateQueueActivity.class);
				startActivity(intent);
				
			}
		});
		Log.i("Debug","Teste 3");
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
					
				Intent intent = new Intent(OwnerActivity.this, CreateControllerActivity.class);
				startActivity(intent);			
								
			}
		});
		
		Log.i("Debug","Teste 4");
		b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
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
