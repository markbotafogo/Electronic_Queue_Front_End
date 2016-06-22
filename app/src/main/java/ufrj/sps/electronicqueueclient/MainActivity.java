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
	
	
	private Button b1, b2, b3, b4;
	final static String TheIP = "192.168.0.7";
	final static String Port = ":8081";
	private String url = "http://" + TheIP  + Port + "/axis2/services/EQCloud/viewQueue";
	
	private final int MY_TICKETS = 1;
	private final int OBSERVER = 2;
	private final int MY_HOME = 3;
	
	private final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("Debug","Teste 1");
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.putExtra("searchType", "ID");
				startActivity(intent);
			}			
		});
		
		Log.i("Debug","Teste 2");
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.putExtra("searchType", "Name");
				startActivity(intent);
			}
		});
		Log.i("Debug","Teste 3");
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
					
				HttpRequester httpRequester = new HttpRequester();
				httpRequester.setRequest(url);
				ArrayList<String> list = httpRequester.getResponses();
				
				Intent intent = new Intent(MainActivity.this, ListQueuesActivity.class);
				intent.putStringArrayListExtra("list", list);
				startActivity(intent);
								
			}
		});
		
		Log.i("Debug","Teste 4");
		b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		Log.i("Debug","Teste 5");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
				
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
						
						Toast.makeText(context, "Please, do the Login first!", Toast.LENGTH_SHORT).show();
						break;
				
				}
				break;
				
			}	
				
		return true;
		
	}
}
