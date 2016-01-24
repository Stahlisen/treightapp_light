package com.example.myfitnessjourney.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by BetaStåhl on 1/12/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView;
        if (convertView == null) {
            LayoutInflater li = ((Activity) mContext).getLayoutInflater();
            mView = li.inflate(R.layout.visual_gridview_layout, null);
            ImageView mImageView = (ImageView) mView.findViewById(R.id.grid_image);
            mImageView.setImageResource(mThumbIds[position]);
            TextView mTextView = (TextView) mView.findViewById(R.id.grid_weight);
        } else {
            mView =  convertView;
        }


        return mView;
    }

    private Integer[] mThumbIds = {
            R.drawable.weighin_image2, R.drawable.weighin_image2,
            R.drawable.weighin_image2, R.drawable.weighin_image2,
            R.drawable.weighin_image2,R.drawable.weighin_image2,
            R.drawable.weighin_image2,R.drawable.weighin_image2,R.drawable.weighin_image2,R.drawable.weighin_image2,R.drawable.weighin_image2,R.drawable.weighin_image2

    };

}
