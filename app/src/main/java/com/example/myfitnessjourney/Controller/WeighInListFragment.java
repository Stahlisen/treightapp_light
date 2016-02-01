package com.example.myfitnessjourney.Controller;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
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
        WeighinCardViewAdapter adapter = new WeighinCardViewAdapter(mWeighIns);
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


    private class WeighinCardViewAdapter extends ArrayAdapter<WeighIn> {
        CardView mCardView;
        TextView mTextView_topTitle;
        TextView mTextView_secondaryTitle;
        TextView mTextView_subText;
        ImageView mImageView;
        Context mContext;
        BitMapFactory bmf;
        ArrayList<WeighIn> weighinList;
        Button mDetailButton;


        public WeighinCardViewAdapter(ArrayList<WeighIn> weighins) {
            super(getActivity(), 0, weighins);
            weighinList = weighins;
            mContext = getActivity();
            bmf = new BitMapFactory();

        }

        //return view for each item in list and set layout and data for all view objects.
        @Override
        public View getView(final int position, View convertView, ViewGroup p) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.weighin_cardview_widget, null);
            }

            WeighIn w = getItem(position);
            mCardView = (CardView) convertView.findViewById(R.id.card_view);
            mTextView_topTitle = (TextView) convertView.findViewById(R.id.cardView_topTitle);
            mTextView_secondaryTitle = (TextView) convertView.findViewById(R.id.cardView_secondaryTitle);
            mTextView_subText = (TextView) convertView.findViewById(R.id.cardView_subText);
            mImageView = (ImageView) convertView.findViewById(R.id.cardView_image);
            mDetailButton = (Button) convertView.findViewById(R.id.cardView_detail_button);

            mDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WeighIn selectedWeighIn = getItem(position);
                    int weighInId = selectedWeighIn.getId();
                    MainActivity.SELECTED_WEIGHIN = weighInId;
                    Bundle bundle = new Bundle();
                    bundle.putInt("weighin_id", weighInId);
                    ((MainActivity) getActivity()).customNavigationCall(R.layout.weighin_detail, bundle);
                }
            });


            mTextView_secondaryTitle.setText(Float.toString(w.getWeight()) + " KG");
            mTextView_subText.setText(w.getDate().toString());
            mTextView_subText.setText(getDaysSinceWeighIn(w.getDate()));
            Log.d("recycler", w.getPicturePath().toString());
            if (!w.getPicturePath().isEmpty() && w.getPicturePath() != null) {
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(bmf.createBitmapFromFilePath(w.getPicturePath()));
            } else {
                mImageView.setVisibility(View.GONE);
            }

            mTextView_topTitle.setText("WEIGHIN #" + w.getId());
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Black.ttf");
            mTextView_topTitle.setTypeface(tf);

            setAnimation(mCardView, position);


            return convertView;
        }

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

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            int lastPosition;
            if (position > position-1)
            {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.animator.test_animation);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle("My Journey");
    }
}
