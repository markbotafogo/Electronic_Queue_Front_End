package ufrj.sps.electronicqueueclient;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

/**
 * Lists the queues found by the SearchActivity
 */

public class ListQueuesActivity extends ListActivity {

    private final static String mUrlName = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueName?search=";

	private ArrayList<String> mQueuesFoundBySearch;
	private ArrayList<Queue> mQueuesInCache;
	private QueueArrayAdapter mAdapter;
	private Queue mSelectedQueue;

	private final Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_queues);
		
		Cache cache = (Cache) getApplicationContext();

		Log.i("Debug","ListQueuesActivity started");
		Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            mQueuesFoundBySearch = doMySearch(query);

        }

		if(mQueuesFoundBySearch == null){
            mQueuesInCache = cache.getAll();
        }
		else{
			
			mQueuesInCache = new ArrayList<>();
			int max = mQueuesFoundBySearch.size();
			for(int i = 0; i < max; i++){

				String[] temp = mQueuesFoundBySearch.get(i).split(":");
				Queue q1 = new Queue(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
				Queue q2 = cache.searchById(q1);
				if (q2 == null)
					mQueuesInCache.add(q1);
				else{
					mQueuesInCache.add(q2);
				}

			}
			
			cache.addAll(mQueuesInCache);
			
		}
		
		mAdapter = new QueueArrayAdapter(this, android.R.layout.simple_list_item_2, mQueuesInCache);
		
		OnItemClickListener itemListener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

				mSelectedQueue = (Queue) parent.getItemAtPosition(position);
				mSelectedQueue.follow(true);
				
				Toast.makeText(mContext, "You have added this queue to the Observer!", Toast.LENGTH_SHORT).show();

			}
		};
		
		OnItemLongClickListener longItemListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3) {

				mSelectedQueue = (Queue) parent.getItemAtPosition(position);
				
				// create a dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("Do you want to enter in this queue?");
				builder.setPositiveButton("Yes", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						boolean ticket = mSelectedQueue.createTicket();
						
						if (!ticket){
							
							Toast.makeText(mContext, "You already got a ticket from this queue!",
                                    Toast.LENGTH_SHORT).show();
							
						}
						else{
							
							Toast.makeText(mContext, "Success!! The ticket was taken.",
                                    Toast.LENGTH_SHORT).show();
							
						}	
						
						dialog.cancel();

					}
					
				});	
				
				builder.setNegativeButton("No", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
					
				});
				
				builder.show();
				
				return true;

			}				
						
		};
					
		getListView().setOnItemClickListener(itemListener);
		getListView().setOnItemLongClickListener(longItemListener);
		
		setListAdapter(mAdapter);
		
	}

    private ArrayList<String> doMySearch (String query){

        HttpRequester httpRequester = new HttpRequester();
        httpRequester.setRequest(mUrlName + query);

        return httpRequester.getResponses();

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_queues, menu);
		return true;

	}
}
