package com.example.myfitnessjourney.Controller;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Model.WeighIn;
import Services.BitMapFactory;
import Services.WeighInLab;
import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

/**
 * Created by fredrikstahl on 15-08-05.
 */
public class WeighInDetailFragment extends Fragment {
    private ImageView mPictureView;
    private TextView mWeight, mDate, mIdView, mCaption, mGained;
    private int mId;
    private WeighIn selectedWeighIn;
    private BitMapFactory bmf;
    private CircleProgressView mCircleView;
    int progresspercentage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.weighin_detail, parent, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState != null) {
            mId = savedInstanceState.getInt("weighinIndex");
        } else {
            mId = getArguments().getInt("weighin_id");
        }

        bmf = new BitMapFactory();

        mWeight = (TextView) view.findViewById(R.id.weighin_weight);
        mIdView = (TextView) view.findViewById(R.id.date_of_weighin);
        mPictureView = (ImageView) view.findViewById(R.id.detail_image);
        mCircleView = (CircleProgressView)view.findViewById(R.id.circleView);

        selectedWeighIn = WeighInLab.get(getActivity()).getWeighInWithId(mId);
        progresspercentage = WeighInLab.get(getActivity()).calculateProgressTowardsGoal(selectedWeighIn.getWeight());

        initiateProgressCircle();
        String imagePath = selectedWeighIn.getPicturePath();

        //If there is an imagepath, get the image, else set to "no photo"
        if (imagePath != null && !imagePath.isEmpty()) {
            mPictureView.setImageBitmap(bmf.createBitmapFromFilePath(selectedWeighIn.getPicturePath()));
        } else {
        }

        String weight_text = Float.toString(selectedWeighIn.getWeight()) + "KG";
        mWeight.setText(weight_text);



        String dateOfWeighin = new SimpleDateFormat("EEE, MMM d, ''yy").format(selectedWeighIn.getDate());

        String id_text = "WEIGHIN #" + selectedWeighIn.getId();
        ((MainActivity) getActivity()).getToolbar_title().setText(id_text);
        mIdView.setText(dateOfWeighin);



        return view;

    }

    //Save data on configuration change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("weighinIndex", mId);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(mIdView.getText());
    }

    //Initiate circle progress view based on weighin stats
    private void initiateProgressCircle() {

        mCircleView.setEnabled(true);
        mCircleView.setText("Loading...");

        mCircleView.setOnAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                mCircleView.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                mCircleView.setUnitVisible(true);
                                break;
                            case SPINNING:
                                mCircleView.setTextMode(TextMode.TEXT); // show text while spinning
                                mCircleView.setUnitVisible(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;

                        }
                    }


                });

        new LongOperation().execute();

        }

        private class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCircleView.setValue(progresspercentage);
                    mCircleView.spin();
                }
            });

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;

        }

            @Override
            protected void onPostExecute(Void aVoid) {
                mCircleView.setValueAnimated(progresspercentage);
            }
        }


}
