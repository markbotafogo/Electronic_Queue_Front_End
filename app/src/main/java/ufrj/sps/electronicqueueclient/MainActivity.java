package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	//Connection settings
	final static String TheIP = "192.168.0.7";
	final static String Port = ":8081";

	private String mUrl = "http://" + TheIP  + Port + "/axis2/services/EQCloud/viewQueue";

	private final Context mContext = this;

	//Menu constants
	private final int MY_TICKETS = 1;
	private final int OBSERVER = 2;
	private final int MY_HOME = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("Debug","Teste 1");
		Button searchQueueByIdButton = (Button) findViewById(R.id.button1);
		searchQueueByIdButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.putExtra("searchType", "ID");
				startActivity(intent);

			}			
		});
		
		Log.i("Debug","Teste 2");
		Button searchQueueByNameButton = (Button) findViewById(R.id.button2);
		searchQueueByNameButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.putExtra("searchType", "Name");
				startActivity(intent);

			}
		});

		Log.i("Debug","Teste 3");
		Button observerActivityButton = (Button) findViewById(R.id.button3);
		observerActivityButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(mUrl);
				ArrayList<String> list = httpRequester.getResponses();
				
				Intent intent = new Intent(MainActivity.this, ListQueuesActivity.class);
				intent.putStringArrayListExtra("list", list);
				startActivity(intent);
								
			}
		});
		
		Log.i("Debug","Teste 4");
		Button loginButton = (Button) findViewById(R.id.button4);
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);

			}
		});

		Log.i("Debug","Teste 5");

	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, MY_TICKETS, 1, "My Tickets");
		menu.add(0, OBSERVER, 2, "Observer");
		menu.add(0, MY_HOME, 3, "My Home");
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId())
		{
		
			case MY_TICKETS:
				
				Intent intent = new Intent(MainActivity.this, ListTicketsActivity.class);
				startActivity(intent);
				break;
				
			case OBSERVER:
				
				Intent intent2 = new Intent(MainActivity.this, ObserverActivity.class);
				startActivity(intent2);
				break;
				
			case MY_HOME:
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				int opt = cache.getUser();
				
				switch (opt){
				
					case Cache.OWNER:
						
						Intent intent3 = new Intent(MainActivity.this, OwnerActivity.class);
						startActivity(intent3);
						break;
						
					case Cache.CONTROLLER:
						
						Intent intent4 = new Intent(MainActivity.this, ControllerActivity.class);
						startActivity(intent4);
						break;
						
					case Cache.NONE:
						
						Toast.makeText(mContext, "Please, do the Login first!", Toast.LENGTH_SHORT).show();
						break;
				
				}

				break;

			}	
				
		return true;
		
	}
}
