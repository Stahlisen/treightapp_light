package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Model.WeighIn;
import Services.BitMapFactory;
import Services.WeighInLab;

/**
 * Created by fredrikstahl on 15-08-05.
 */
public class WeighInDetailFragment extends Fragment {
    private ImageView mPictureView;
    private TextView mWeight, mDate, mIdView, mCaption;
    private int mId;
    private WeighIn selectedWeighIn;
    private BitMapFactory bmf;
    private String WEIGHINTITLE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.weighin_detail, parent, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        //Get the selected id from arguments and set all view elements
        mId = getArguments().getInt("weighin_id");
        bmf = new BitMapFactory();
        selectedWeighIn = WeighInLab.get(getActivity()).getWeighInWithId(mId);
        WEIGHINTITLE = "WEIGHIN #" + selectedWeighIn.getId();
        mCaption = (TextView) view.findViewById(R.id.photo_caption);
        mPictureView = (ImageView) view.findViewById(R.id.detail_image);
        String path = selectedWeighIn.getPicturePath();

        //If there is an imagepath, get the image, else set to "no photo"
        if (path != null && !path.isEmpty()) {
            mPictureView.setImageBitmap(bmf.createBitmapFromFilePath(selectedWeighIn.getPicturePath()));
        } else {
            mCaption.setText("NO PHOTO FOR THIS WEIGH IN");
        }

        mWeight = (TextView) view.findViewById(R.id.detail_weight);
        String weight_text = Float.toString(selectedWeighIn.getWeight()) + "KG";
        mWeight.setText(weight_text);
        mDate = (TextView) view.findViewById(R.id.detail_date);

        String date_text = selectedWeighIn.getDate().toString();
        mDate.setText(date_text);

        mIdView = (TextView) view.findViewById(R.id.detail_id);
        String id_text = "WEIGHIN #" + selectedWeighIn.getId();
        mIdView.setText(id_text);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(WEIGHINTITLE);
    }

}
