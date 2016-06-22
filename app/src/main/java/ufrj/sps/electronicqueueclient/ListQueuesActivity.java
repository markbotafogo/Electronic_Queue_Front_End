package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;

import ufrj.sps.electronicqueueclient.Queue.Ticket;

import android.app.AlertDialog;
import android.app.ListActivity;
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


public class ListQueuesActivity extends ListActivity {

	ArrayList<String> list;
	ArrayList<Queue> scroll;
	QueueArrayAdapter adapter;
	Queue selectedItem;
	private final Context context = this;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_list_queues);
		
		Cache cache = (Cache) getApplicationContext();
		cache = cache.getInstance();

		Log.i("Debug","ListQueuesActivity started");
		
		Intent data = getIntent();
		
		list = data.getStringArrayListExtra("list");
		
		if(list == null)
			scroll = cache.getAll();
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
		
		adapter = new QueueArrayAdapter(this, android.R.layout.simple_list_item_2, scroll);
		
		OnItemClickListener itemListener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				selectedItem = (Queue) parent.getItemAtPosition(position);
				
				selectedItem.follow(true);
				
				Toast.makeText(context, "You have added this queue to the Observer!", Toast.LENGTH_SHORT).show();
				
			
			}
		
		};
		
		OnItemLongClickListener longItemListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				selectedItem = (Queue) parent.getItemAtPosition(position);
				
				// create a dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Do you want to enter in this queue?");
				builder.setPositiveButton("Yes", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Cache cache = (Cache) getApplicationContext();
						cache = cache.getInstance();
						Ticket ticket = selectedItem.createTicket();
						
						if (ticket == null){
							
							Toast.makeText(context, "You already got a ticket from this queue!", Toast.LENGTH_SHORT).show();
							
						}
						else{
							
							Toast.makeText(context, "Success!! The ticket was taken.", Toast.LENGTH_SHORT).show();
							cache.addTicket(ticket);
							
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
		
		setListAdapter(adapter);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_queues, menu);
		return true;
	}

}
