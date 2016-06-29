package ufrj.sps.electronicqueueclient;

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

import java.util.ArrayList;

/**
 * Realizes a search of queues by identifier(id) or by name in the remote database through http
 * requisition
 */

public class SearchActivity extends Activity {

    private String mUrlName = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueName?search=";
    private String mUrlID = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueId?search=";

	private EditText mSearchField;

    private String mType, mSearch;
    private ArrayList<String> mList;
    
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);

		Intent data = getIntent();
		mType = data.getStringExtra("searchType");
		
		mSearchField = (EditText) findViewById(R.id.editText2);
		TextView searchTypeView = (TextView) findViewById(R.id.textView1);
		
		if (mType.equals("ID"))
			searchTypeView.setText(R.string.searchID);
		else
			searchTypeView.setText(R.string.searchName);
		
		Button searchButton = (Button) findViewById(R.id.button1);
		searchButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				mSearch = mSearchField.getText().toString();
				HttpRequester httpRequester = new HttpRequester();				
				
				if (mType.equals("ID"))
					httpRequester.setRequest(mUrlID + mSearch);
				else				
					httpRequester.setRequest(mUrlName + mSearch);
				
				mList = httpRequester.getResponses();
				
				Intent intent = new Intent(SearchActivity.this, ListQueuesActivity.class);
				intent.putStringArrayListExtra("list", mList);
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
