package com.example.myfitnessjourney.Controller;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import Model.WeighIn;
import Services.BitMapFactory;
import Services.WeighInLab;


public class WeighInListFragment extends ListFragment {
    private ArrayList<WeighIn> mWeighIns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get all weighins from weighinlab and reverse the order so that the latest weighin comes first
        WeighInLab.get(getActivity()).getAllWeighIns(getActivity());
        mWeighIns = WeighInLab.get(getActivity()).getWeighins();
        Collections.reverse(mWeighIns);
        WeighInAdapter adapter = new WeighInAdapter(mWeighIns);
        setListAdapter(adapter);
        setHasOptionsMenu(true);
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
                //If user clicks on the plus sign, call navigationcaller
                ((MainActivity) getActivity()).customNavigationCall(R.id.action_add, null);
                break;
        }
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //If user clicks a weighin, get the id of the weighin and send as bundle to navigationcall
        WeighIn selectedWeighIn = (WeighIn) getListAdapter().getItem(position);
        int weighInId = selectedWeighIn.getId();
        MainActivity.SELECTED_WEIGHIN = weighInId;
        Bundle bundle = new Bundle();
        bundle.putInt("weighin_id", weighInId);
        ((MainActivity) getActivity()).customNavigationCall(R.layout.weighin_detail, bundle);
    }

    private class WeighInAdapter extends ArrayAdapter<WeighIn> {
        TextView weightTextView, dateTextView, idTextView;
        ImageView weight_image;
        BitMapFactory bmf = new BitMapFactory();

        public WeighInAdapter(ArrayList<WeighIn> weighins) {
            super(getActivity(), 0, weighins);

        }

        //return view for each item in list and set layout and data for all view objects.
        @Override
        public View getView(int position, View convertView, ViewGroup p) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.weighin_list_item, null);
            }

            WeighIn w = getItem(position);

            weightTextView = (TextView) convertView.findViewById(R.id.weight_item_value);
            String weight = Float.toString(w.getWeight()) + "KG";
            weightTextView.setText(weight);

            dateTextView = (TextView) convertView.findViewById(R.id.date_item_value);
            dateTextView.setText(getDaysSinceWeighIn(w.getDate()));

            idTextView = (TextView) convertView.findViewById(R.id.id_item_value);
            String id = "WEIGH IN #" + Integer.toString(w.getId());
            idTextView.setText(id);

            weight_image = (ImageView) convertView.findViewById(R.id.weighin_image_view);
            if (w.getPicturePath() != null) {
                weight_image.setImageBitmap(bmf.createBitmapFromFilePath(w.getPicturePath()));
            }

            return convertView;
        }

        //Method to get days since last weighin
        public String getDaysSinceWeighIn(Date date) {

            long timeOne = date.getTime();
            long timeTwo = new Date().getTime();
            long oneDay = 1000 * 60 * 60 * 24;
            long delta = (timeTwo - timeOne) / oneDay;
            String text = null;

            if (delta > 0) {

                text = delta + " days ago";
            } else {
                text = "Today";
            }
            return text;


        }


    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle("My Journey");
    }
}
