package com.example.myfitnessjourney.Controller;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import Model.HomeUserInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeInfoFragment extends Fragment {
	TextView mWelcomeText, mCurrentWeight, mGoalWeight, mGoalDate,
			mTimeRemaining;
	String loginid;
	HomeUserInfo userInfo;

	public static HomeInfoFragment newInstance(String login_id) {
		Bundle args = new Bundle();
		args.putString("login_id", login_id);

		HomeInfoFragment fragment = new HomeInfoFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loginid = (String) getArguments().getString("login_id");

		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, parent, savedInstanceState);

		View v = inflater.inflate(R.layout.fragment_home_info, parent, false);

		mWelcomeText = (TextView) v.findViewById(R.id.welcome_text);
		mCurrentWeight = (TextView) v.findViewById(R.id.current_weight_v);
		mGoalWeight = (TextView) v.findViewById(R.id.goal_weight_v);
		mGoalDate = (TextView) v.findViewById(R.id.goal_date_v);
		mTimeRemaining = (TextView) v.findViewById(R.id.time_remaining_v);
		setHomeUserInfo();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Welcome ");
		stringBuilder.append(loginid);

		String welcome_text = stringBuilder.toString();
		mWelcomeText.setText(welcome_text);

		return v;

	}

	public void setHomeUserInfo() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Goal");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.getFirstInBackground(new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					Log.d("query", "The getFirst request failed.");
				} else {
					Log.d("query", "success");
					
					String currentweight = object.get("weight").toString();
					mCurrentWeight.setText(currentweight + "kg");
					
					String goalweight = object.get("weight").toString();
					mGoalWeight.setText(goalweight + "kg");
					
					String goaldate = object.get("goaldate").toString();
					mGoalDate.setText(goaldate);
					
					String timeremaining = "1 day(s)";
					mTimeRemaining.setText(timeremaining);
				}
			}

		});

	}

}
