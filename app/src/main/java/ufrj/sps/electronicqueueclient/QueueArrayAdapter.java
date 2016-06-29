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
 * Adapts the data of the queues for exhibition in a list showing the name and the identifier(id)
 */

class QueueArrayAdapter extends ArrayAdapter<Queue> {

	private Context mContext;

	private List<Queue> mQueuesList = new ArrayList<Queue>();
	
    QueueArrayAdapter(Context context, int textViewResourceId, List<Queue> objects) {

	    super(context, textViewResourceId, objects);
	    this.mContext = context;
	    this.mQueuesList = objects;
	    Collections.sort(mQueuesList);

	}
	
	public int getCount()
	{
	    return this.mQueuesList.size();
	}
	
	public Queue getItem(int index) {

	    if (index <= getCount()) return this.mQueuesList.get(index); //IndexOutOfBoundsException fix
	    return this.mQueuesList.get(getCount() - 1);

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {

	    View row = convertView;

	    if (row == null) {

	        // ROW INFLATION
	        LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

	    }

	    // Get item
	    Queue queue = getItem(position);

	    TextView queueName = (TextView) row.findViewById(android.R.id.text1);   //change this to textField1  from simple_list_item_2
	    queueName.setText(queue.getName());

	    TextView queueId = (TextView) row.findViewById(android.R.id.text2); //change this to textField2 from simple_list_item_2
	    queueId.setText(R.string.id + Integer.toString(queue.getID()));

	    return row;

	}
}
