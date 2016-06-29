package ufrj.sps.electronicqueueclient;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import java.util.ArrayList;

/**
 * Lists the queues added by the user in the Observer. This activity live displays the queues
 * showing to the user the progress of the selected ones.
 */

public class ObserverActivity extends ListActivity {

	private ArrayList<String> mQueuesFound;
	private ArrayList<Queue> mQueuesInCache;
	private ObserverAdapter mAdapter;
	private Queue mSelectedItem;

	private final Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_observer);
		
		Cache cache = (Cache) getApplicationContext();
		cache = cache.getInstance();
		cache.setRunning(false);

		Log.i("Debug","ObserverActivity started");
		
		Intent data = getIntent();
		mQueuesFound = data.getStringArrayListExtra("list");
		
		if (mQueuesFound == null){
			
			mQueuesInCache = cache.getAll();
			
			for(int i = 0; i < mQueuesInCache.size(); i++){

				Queue queue = mQueuesInCache.get(i);
				
				if (!(queue.isFollowing())) mQueuesInCache.remove(i);

			}
			
		}
		else{
			
			mQueuesInCache = new ArrayList<Queue>();
			int max = mQueuesFound.size();
			for(int i = 0; i < max; i++){

				String[] temp = mQueuesFound.get(i).split(":");
				Queue q1 = new Queue(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
                        Integer.parseInt(temp[3]));
				Queue q2 = cache.searchById(q1);

				if (q2 == null)
					mQueuesInCache.add(q1);
				else{
					mQueuesInCache.add(q2);
				}

			}
			
			cache.addAll(mQueuesInCache);
			
		}
		
		
		mAdapter = new ObserverAdapter(this, android.R.layout.simple_list_item_2, mQueuesInCache);
		
		OnItemLongClickListener longItemListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long arg3) {

				mSelectedItem = (Queue) parent.getItemAtPosition(position);

				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				if(cache.isUserRegistered()){
					
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setMessage("Do you want to make this queue to advance?");
					builder.setPositiveButton("Yes", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							mSelectedItem.consume();
							Toast.makeText(mContext, "The queue advanced.", Toast.LENGTH_SHORT).show();
							
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
					
				}
				else{
					
					// create a dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setMessage("Do you want to remove this queue from Observer?");
					builder.setPositiveButton("Yes", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							mSelectedItem.follow(false);
							mAdapter.remove(mSelectedItem);
							
							Toast.makeText(mContext, "The queue was removed from the Observer",
                                    Toast.LENGTH_SHORT).show();
							
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
					
				}
				
				return true;

			}				
						
		};
					
		getListView().setOnItemLongClickListener(longItemListener);
		
		setListAdapter(mAdapter);
		
	}

	public void onResume() {
		
		super.onResume();
		
		Cache cache = (Cache) getApplicationContext();
		cache = cache.getInstance();
		cache.setRunning(true);
		
		final Handler handler = new Handler();
		handler.post(new Runnable(){

			@Override
			public void run() {
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				if(cache.isRunning()){

                    for(int i = 0; i < mAdapter.getCount(); i++){mAdapter.getItem(i).update();}

                }

				mAdapter.notifyDataSetChanged();
				handler.postDelayed(this, 1000);
				
			}
						
		});
	}
	
	public void onPause(){
		
		super.onPause();

        Cache cache = (Cache) getApplicationContext();
        cache = cache.getInstance();
		cache.setRunning(false);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.observer, menu);
		return true;

	}
}
