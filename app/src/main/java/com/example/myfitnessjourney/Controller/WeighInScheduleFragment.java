package com.example.myfitnessjourney.Controller;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import Model.Alarm;
import Services.WeighInLab;

/**
 * Created by fredrikstahl on 15-12-09.
 */
public class WeighInScheduleFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("log_1", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        View view = populateViewForOrientation(inflater, frameLayout);
        setHasOptionsMenu(true);

        Context applicationContext = getActivity();
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.recyclerView);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(applicationContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        WeighInLab.get(getActivity()).getAllWeighIns(getActivity());
        List<Alarm> alarmList = WeighInLab.get(getActivity()).getAlarms();
        //Log.d("recycler", "size:" + Integer.toString(alarmList.size()));
        RecyclerViewAdapterSchedule rvas = new RecyclerViewAdapterSchedule(alarmList, getActivity());
        recList.setAdapter(rvas);

        return frameLayout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.weighin_scheduler, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_add_alarm:
                Log.d("new_alarm", "onOptionsItemsSelected");
                //If user clicks on the plus sign, call navigationcaller
                ((MainActivity) getActivity()).customNavigationCall(R.id.action_add_alarm, null);
                break;
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    public View populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View view = inflater.inflate(R.layout.recyclerview, viewGroup);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle("My Weigh In Schedule");
    }
}
