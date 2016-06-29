package ufrj.sps.electronicqueueclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapts the data of the queues for exhibition in a list showing the last called password and the
 * next password available.
 */

class ObserverAdapter extends QueueArrayAdapter{
	
    ObserverAdapter(Context context, int textViewResourceId, List<Queue> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	    View row = convertView;

	    if (row == null) {

	        // ROW INFLATION
	        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

	    }

	    // Get item
	    Queue queue = getItem(position);
	    queue.update();

	    TextView queueName = (TextView) row.findViewById(android.R.id.text1);   //change this to textField1  from simple_list_item_2
	    queueName.setText(queue.getName());

	    TextView queueActualNumber = (TextView) row.findViewById(android.R.id.text2); //change this to textField2 from simple_list_item_2
	    queueActualNumber.setText("Actual Number: " + Integer.toString(queue.getNumber()) +
                "      Next Password: " + Integer.toString(queue.getNextPassword()));

	    return row;

	}
}