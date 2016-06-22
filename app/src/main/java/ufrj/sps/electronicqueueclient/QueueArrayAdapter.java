package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QueueArrayAdapter extends ArrayAdapter<Queue> {

	private static final String tag         = "QueueArrayAdapter";
	private Context             context;

	protected TextView          queueName;
	private TextView            queueID;
	private List<Queue>         listQueues     = new ArrayList<Queue>();
	
	public QueueArrayAdapter(Context context, int textViewResourceId, List<Queue> objects)
	{
	    super(context, textViewResourceId, objects);
	    this.context = context;
	    this.listQueues = objects;	    
	    Collections.sort(listQueues);
	}
	
	public int getCount()
	{
	    return this.listQueues.size();
	}
	
	public Queue getItem(int index)
	{
	    if (index <= getCount())    //IndexOutOfBoundsException fix
	        return this.listQueues.get(index);
	    return this.listQueues.get(getCount() - 1);
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
	    Queue queue = getItem(position);
	    //queue.update();

	    queueName = (TextView) row.findViewById(android.R.id.text1);   //change this to textField1  from simple_list_item_2
	    queueName.setText(queue.getName());

	    queueID = (TextView) row.findViewById(android.R.id.text2); //change this to textField2 from simple_list_item_2
	    queueID.setText("ID: " + Integer.toString(queue.getID()));
	    //      Log.d(tag, buddy.getIdentity()+"'s mood is "+buddyStatus.getText());



	    return row;
	}
}
