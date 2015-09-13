package com.example.myfitnessjourney.Controller;

import java.util.ArrayList;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HomeListFragment extends ListFragment {
	private ArrayList<String> activities;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activities = new ArrayList<String>();
		activities.add("Changed your goal weight to 58kg");
		activities.add("Changed your goal weight to 68kg");
		activities.add("Changed your goal weight to 78kg");
		activities.add("Changed your goal weight to 88kg");
		
		ActivityAdapter adapter = new ActivityAdapter(activities);
		setListAdapter(adapter);

	}
	
	
	private class ActivityAdapter extends ArrayAdapter<String> {
		
		public ActivityAdapter(ArrayList<String> activities) {
			super(getActivity(), 0, activities);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup p) {
			
			
			if (convertView == null) {
			convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_home,p, false);

			}
			
			String s = getItem(position);
			
			TextView mActivityText = (TextView) convertView.findViewById(R.id.activity_text);
			mActivityText.setText(s);
			//TextView mDaysAgo = (TextView) convertView.findViewById(R.id.activity_days_ago);
			
			return convertView;
		}
	}

}
