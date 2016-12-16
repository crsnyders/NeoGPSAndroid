package za.co.crsnyders.neogps;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import za.co.crsnyders.neogps.*;

public class TextAdapter extends ArrayAdapter<String>
{
	public TextAdapter(Context context) {
		super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		String text = getItem(position);    
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item, parent, false);
		}
		// Lookup view for data population
		TextView tvName = (TextView) convertView.findViewById(R.id.item);
		
		// Populate the data into the template view using the data object
		tvName.setText(text);
		
		// Return the completed view to render on screen
		return convertView;
	}
	
}
