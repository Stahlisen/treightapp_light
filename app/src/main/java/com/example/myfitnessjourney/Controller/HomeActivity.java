package com.example.myfitnessjourney.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class HomeActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private String[] mViews;


	@Override
	public void onCreate (Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_fragment);

		Intent i = new Intent(this, ProgressListActivity.class);
		startActivity(i);


	FragmentManager fm = getSupportFragmentManager();
	
	//Add HomeInfoFragment fragment
	Fragment fragment1 = fm.findFragmentById(R.id.fragmentContainer1);
	
	if (fragment1 == null) {
		fragment1 = createFragment(1);
		fm.beginTransaction()
		.add(R.id.fragmentContainer1, fragment1)
		.commit();
	}
	
	Fragment fragment2 = fm.findFragmentById(R.id.fragmentContainer2);
	
	if (fragment2 == null) {
		fragment2 = createFragment(2);
		fm.beginTransaction()
		.add(R.id.fragmentContainer2, fragment2)
		.commit();
	}
	}

	protected Fragment createFragment(int fragmentno) {
		if (fragmentno == 1) {
		// TODO Auto-generated method stub
		String loginid = getIntent().getStringExtra(LoginActivity.LOGIN_ID);
		
		return HomeInfoFragment.newInstance(loginid);
		} else {
			return new HomeListFragment();
		}
	}


	
	


}
