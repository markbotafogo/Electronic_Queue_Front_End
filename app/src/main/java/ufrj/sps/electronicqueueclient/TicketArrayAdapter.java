package ufrj.sps.electronicqueueclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapts the data of the queues with tickets for exhibition in a list showing the password of the
 * ticket and the name of queue that it belongs
 */

public class TicketArrayAdapter extends ArrayAdapter<Queue>{

	private Context mContext;

	private List<Queue> mListQueuesWithTicket = new ArrayList<>();
	
    TicketArrayAdapter(Context context, int textViewResourceId, List<Queue> objects) {

	    super(context, textViewResourceId, objects);
	    this.mContext = context;
	    this.mListQueuesWithTicket = objects;
	    Collections.sort(mListQueuesWithTicket);

	}
	
	public int getCount() {
        return this.mListQueuesWithTicket.size();
	}
	
	public Queue getItem(int index) {

	    if (index <= getCount())    //IndexOutOfBoundsException fix
	        return this.mListQueuesWithTicket.get(index);
	    return this.mListQueuesWithTicket.get(getCount() - 1);

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {

	    View row = convertView;

	    if (row == null) {

	        // ROW INFLATION
	        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

	    }

	    // Get item
	    Queue item = getItem(position);

	    TextView ticketName = (TextView) row.findViewById(android.R.id.text1);   //change this to textField1  from simple_list_item_2
	    ticketName.setText(item.getName());

	    TextView ticketPassword = (TextView) row.findViewById(android.R.id.text2); //change this to textField2 from simple_list_item_2
	    ticketPassword.setText(R.string.your_password + Integer.toString(item.getTicket()));

	    return row;

	}
}
