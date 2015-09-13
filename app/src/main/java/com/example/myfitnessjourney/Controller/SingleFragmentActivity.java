package com.example.myfitnessjourney.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

public abstract class SingleFragmentActivity extends ActionBarActivity {
	protected abstract Fragment createFragment();

	@Override
	public void onCreate (Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.md_activity_fragment);
	FragmentManager fm = getSupportFragmentManager();
	Fragment fragment = fm.findFragmentById(R.id.md_fragmentContainer);

		if (fragment == null) {
		fragment = createFragment();
		fm.beginTransaction()
		.add(R.id.md_fragmentContainer, fragment)
		.commit();
	}
	}




}
