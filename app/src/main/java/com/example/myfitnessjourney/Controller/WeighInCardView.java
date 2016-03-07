package com.example.myfitnessjourney.Controller;


import android.content.Context;
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
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import Model.WeighIn;
import Services.WeighInLab;

public class WeighInCardView extends Fragment {

    TextView mCurrentWeight, mSinceStart, mDaysLeft, mGoalWeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weighins_recyclerview, container, false);
        Context applicationContext = getActivity();

        mCurrentWeight = (TextView) view.findViewById(R.id.current_weight_value);
        mSinceStart = (TextView) view.findViewById(R.id.since_start_value);
        mDaysLeft = (TextView) view.findViewById(R.id.days_left_value);
        mGoalWeight = (TextView) view.findViewById(R.id.goal_weight_value);

        LinearLayoutManager llm = new LinearLayoutManager(applicationContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recList = (RecyclerView) view.findViewById(R.id.recyclerView);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(llm);

        WeighInLab.get(getActivity()).getAllWeighIns(getActivity());
        List<WeighIn> weighinList = WeighInLab.get(getActivity()).getWeighins();
        Collections.reverse(weighinList);

        Float currentWeight = WeighInLab.get(getActivity()).getCurrentWeight();
        String currentWeightString = Float.toString(currentWeight);

        mCurrentWeight.setText(currentWeightString);


        RecyclerViewAdapter rva = new RecyclerViewAdapter(weighinList, getActivity(), "WeighIns");
        recList.setAdapter(rva);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.progress, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_add:
                ((MainActivity) getActivity()).customNavigationCall(R.id.action_add, null);
                break;
        }
        return true;
    }
}
