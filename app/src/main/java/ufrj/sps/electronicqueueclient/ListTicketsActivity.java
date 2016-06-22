package ufrj.sps.electronicqueueclient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ufrj.sps.electronicqueueclient.Queue.Ticket;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListTicketsActivity extends ListActivity {

	ArrayList<Ticket> list;
	ArrayList<Queue> scroll;
	TicketArrayAdapter adapter;
	Ticket selectedItem;
	private final Context context = this;	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_list_tickets);
		
		Cache cache = (Cache) getApplicationContext();
		cache = cache.getInstance();

		Log.i("Debug","ListTicketsActivity started");
		
		scroll = cache.getAll();
		list = new ArrayList<Ticket>();

		for(int i = 0; i < scroll.size(); i++){

			Calendar thisMoment = Calendar.getInstance();
			Ticket ticket = scroll.get(i).getTicket();
			
			if(ticket != null){
				
				Date date = ticket.getTimeOfCreation();
				
				if(thisMoment.get(Calendar.DAY_OF_MONTH) > date.getDate() && 
				   thisMoment.get(Calendar.MONTH) >= date.getMonth() && 
				   thisMoment.get(Calendar.YEAR) >= date.getYear() ){
							
					scroll.get(i).removeTicket();
							
				}
				else{
					
					list.add(ticket);
					
				}
				
			}

		}

		
		adapter = new TicketArrayAdapter(this, android.R.layout.simple_list_item_2, list);
		
		OnItemClickListener itemListener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				selectedItem = (Ticket) parent.getItemAtPosition(position);
				
				DateFormat format = DateFormat.getDateTimeInstance();
				
				Toast.makeText(context, "QueueID:" + selectedItem.getID() + "\r\n" + "Date of Creation:" + format.format(selectedItem.getTimeOfCreation()), 
						Toast.LENGTH_SHORT).show();
							
			}
		
		};
		
		OnItemLongClickListener longItemListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				selectedItem = (Ticket) parent.getItemAtPosition(position);
				
				// create a dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Do you want to delete this ticket?");
				builder.setPositiveButton("Yes", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Cache cache = (Cache) getApplicationContext();
						cache = cache.getInstance();
						
						Queue queue = cache.searchById(selectedItem.getID());
						queue.removeTicket();
						
						adapter.remove(selectedItem);
						
						Toast.makeText(context, "The ticket was deleted.", Toast.LENGTH_SHORT).show();
						
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
		getMenuInflater().inflate(R.menu.list_tickets, menu);
		return true;
		
	}

}
	
