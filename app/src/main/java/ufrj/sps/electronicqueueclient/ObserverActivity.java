package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;

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

public class ObserverActivity extends ListActivity {

	ArrayList<String> list;
	ArrayList<Queue> scroll;
	ObserverAdapter adapter;
	Queue selectedItem;
	private final Context context = this;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_observer);
		
		Cache cache = (Cache) getApplicationContext();
		cache = cache.getInstance();
		
		cache.setRunning(false);

		Log.i("Debug","ObserverActivity started");
		
		Intent data = getIntent();
		list = data.getStringArrayListExtra("list");
		
		if (list == null){
			
			scroll = cache.getAll();
			
			for(int i = 0; i < scroll.size(); i++){

				Queue queue = scroll.get(i);
				
				if (!(queue.isFollowing()))			
					scroll.remove(i);
					
				
			}
			
		}
		else{
			
			scroll = new ArrayList<Queue>();
			int max = list.size();
			for(int i = 0; i < max; i++){

				String[] temp = list.get(i).split(":");
				Queue q1 = new Queue(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
				Queue q2 = cache.searchById(q1);
				if (q2 == null)
					scroll.add(q1);
				else{
					scroll.add(q2);
				}
			}
			
			cache.addAll(scroll);
			
		}
		
		
		adapter = new ObserverAdapter(this, android.R.layout.simple_list_item_2, scroll);
		
		OnItemLongClickListener longItemListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				selectedItem = (Queue) parent.getItemAtPosition(position);
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				if(cache.isUserRegistered()){
					
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Do you want this queue to walk?");
					builder.setPositiveButton("Yes", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							selectedItem.consume();							
								
							Toast.makeText(context, "The queue was consumed.", Toast.LENGTH_SHORT).show();							
							
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
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Do you want to remove this queue from Observer?");
					builder.setPositiveButton("Yes", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							selectedItem.follow(false);
							adapter.remove(selectedItem);
							
							Toast.makeText(context, "The queue was removed from the Observer", Toast.LENGTH_SHORT).show();							
							
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
		
		setListAdapter(adapter);
		
/*		for(;;){
			
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				
				}
			};
			new Timer().schedule(task, 1000, 1000);
						
		}*/
		
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
				// TODO Auto-generated method stub
				
				Cache cache = (Cache) getApplicationContext();
				cache = cache.getInstance();
				
				if(cache.isRunning())
				for(int i = 0; i < adapter.getCount(); i++){
					
					adapter.getItem(i).update();
					
				}
				
				adapter.notifyDataSetChanged();
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
