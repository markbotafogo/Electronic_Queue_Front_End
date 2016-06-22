package ufrj.sps.electronicqueueclient;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ObserverAdapter extends QueueArrayAdapter{

	private TextView queueActualNumber;
	
	public ObserverAdapter(Context context, int textViewResourceId,
			List<Queue> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
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
	    queue.update();

	    super.queueName = (TextView) row.findViewById(android.R.id.text1);   //change this to textField1  from simple_list_item_2
	    super.queueName.setText(queue.getName());

	    queueActualNumber = (TextView) row.findViewById(android.R.id.text2); //change this to textField2 from simple_list_item_2
	    queueActualNumber.setText("Actual Number: " + Integer.toString(queue.getNumber()) + "        Next Password: " + Integer.toString(queue.getNextPassword()));
	    //      Log.d(tag, buddy.getIdentity()+"'s mood is "+buddyStatus.getText());



	    return row;
	}

}
