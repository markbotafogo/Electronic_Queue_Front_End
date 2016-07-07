package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//Connection settings
	final static String TheIP = "192.168.0.7";
	final static String Port = ":8081";

	private String mUrl = "http://" + TheIP  + Port + "/axis2/services/EQCloud/viewQueue";

	private final Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i("Debug", "Teste 1");
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

		getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName
                (this, ListQueuesActivity.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.setQueryHint("Search Queue");

		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId())
		{
				
			case R.id.action_my_home:

				Cache cache = (Cache) getApplicationContext();
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
