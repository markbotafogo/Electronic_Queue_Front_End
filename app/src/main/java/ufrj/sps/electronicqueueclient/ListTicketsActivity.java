package ufrj.sps.electronicqueueclient;

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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Lists the tickets got by the user.
 */

public class ListTicketsActivity extends ListActivity {

	private ArrayList<Queue> mQueuesWithTicket;
	private ArrayList<Queue> mQueuesInCache;
	private TicketArrayAdapter mAdapter;
	private Queue mSelectedItem;

	private final Context mContext = this;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tickets);
		
		Cache cache = (Cache) getApplicationContext();

		Log.i("Debug","ListTicketsActivity started");
		
		mQueuesInCache = cache.getAll();
		mQueuesWithTicket = new ArrayList<>();

		for(int i = 0; i < mQueuesInCache.size(); i++){

			Calendar thisMoment = Calendar.getInstance();
			int ticket = mQueuesInCache.get(i).getTicket();
			
			if(ticket != -1){
				
				Date date = mQueuesInCache.get(i).getTicketTimeOfCreation();
				
				if(thisMoment.get(Calendar.DAY_OF_MONTH) > date.getDate() &&
                        thisMoment.get(Calendar.MONTH) >= date.getMonth() &&
                        thisMoment.get(Calendar.YEAR) >= date.getYear()){
							
					mQueuesInCache.get(i).removeTicket();
							
				}
				else{
					mQueuesWithTicket.add(mQueuesInCache.get(i));
				}
			}
		}
		
		mAdapter = new TicketArrayAdapter(this, android.R.layout.simple_list_item_2, mQueuesWithTicket);
		
		OnItemClickListener itemListener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

				mSelectedItem = (Queue) parent.getItemAtPosition(position);
				
				DateFormat format = DateFormat.getDateTimeInstance();
				
				Toast.makeText(mContext, "QueueID:" + mSelectedItem.getID() + "\r\n" +
                        "Date of Creation:" + format.format(mSelectedItem.getTicketTimeOfCreation()),
						Toast.LENGTH_SHORT).show();
							
			}
		
		};
		
		OnItemLongClickListener longItemListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long arg3) {

				mSelectedItem = (Queue) parent.getItemAtPosition(position);
				
				// create a dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("Do you want to delete this ticket?");
				builder.setPositiveButton("Yes", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						mSelectedItem.removeTicket();
						mAdapter.remove(mSelectedItem);
						
						Toast.makeText(mContext, "The ticket was deleted.", Toast.LENGTH_SHORT).show();
						
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_tickets, menu);
		return true;

	}
}
	
