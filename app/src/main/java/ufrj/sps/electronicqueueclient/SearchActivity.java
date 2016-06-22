package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;


public class SearchActivity extends Activity {

	EditText et;
	TextView tv;
    Button b;
    String urlName = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueName?search=";
    String urlID = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueId?search=";
    String opt, search;
    ArrayList<String> list;
    
    
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);

		Intent data = getIntent();
		
		opt = data.getStringExtra("searchType");
		
		et = (EditText) findViewById(R.id.editText2);
		
		tv = (TextView) findViewById(R.id.textView1);
		
		if (opt.equals("ID"))
			tv.setText(R.string.searchID);		
		else
			tv.setText(R.string.searchName);
		
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search = et.getText().toString();
				HttpRequester httpRequester = new HttpRequester();				
				
				if (opt.equals("ID"))
					httpRequester.setRequest(urlID + search);				
				else				
					httpRequester.setRequest(urlName + search);
				
				list = httpRequester.getResponses();
				
				Intent intent = new Intent(SearchActivity.this, ListQueuesActivity.class);
				intent.putStringArrayListExtra("list", list);
				startActivity(intent);
				
			}
			
		});
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
