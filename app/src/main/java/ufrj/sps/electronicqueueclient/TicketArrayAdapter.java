package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ufrj.sps.electronicqueueclient.Queue.Ticket;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TicketArrayAdapter extends ArrayAdapter<Ticket>{

	private static final String tag         = "TicketArrayAdapter";
	private Context             context;

	private TextView            ticketName;
	private TextView            ticketPassword;
	private List<Ticket>        listTickets     = new ArrayList<Ticket>();
	
	public TicketArrayAdapter(Context context, int textViewResourceId, List<Ticket> objects)
	{
	    super(context, textViewResourceId, objects);
	    this.context = context;
	    this.listTickets = objects;	    
	    Collections.sort(listTickets);
	}
	
	public int getCount()
	{
	    return this.listTickets.size();
	}
	
	public Ticket getItem(int index)
	{
	    if (index <= getCount())    //IndexOutOfBoundsException fix
	        return this.listTickets.get(index);
	    return this.listTickets.get(getCount() - 1);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View row = convertView;

	    if (row == null)
	    {
	        // ROW INFLATION
	        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
	    }

	    // Get item
	    Ticket ticket = getItem(position);
	    //queue.update();

	    ticketName = (TextView) row.findViewById(android.R.id.text1);   //change this to textField1  from simple_list_item_2
	    ticketName.setText(ticket.getName());

	    ticketPassword = (TextView) row.findViewById(android.R.id.text2); //change this to textField2 from simple_list_item_2
	    ticketPassword.setText("Your password:" + Integer.toString(ticket.getPassword()));
	    //      Log.d(tag, buddy.getIdentity()+"'s mood is "+buddyStatus.getText());



	    return row;
	}
}
