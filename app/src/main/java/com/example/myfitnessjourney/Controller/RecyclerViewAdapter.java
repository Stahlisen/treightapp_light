package com.example.myfitnessjourney.Controller;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import Model.WeighIn;
import Services.BitMapFactory;
import Services.WeighInLab;

/**
 * Created by fredrikstahl on 15-11-28.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WeighInViewHolder> {
    List<WeighIn> listOfWeighIns;
    BitMapFactory bmf;
    Context mContext;

    RecyclerViewAdapter(List<WeighIn> weighins, Context context, String entityType) {
        //Collections.reverse(weighins);
        this.listOfWeighIns = weighins;
        for (WeighIn w : listOfWeighIns) {
            Log.d("recycler", Integer.toString(w.getId()));
        }
        String s = Integer.toString(getItemCount());
        Log.d("recycler", s + "reverse");
        bmf = new BitMapFactory();
        mContext = context;
    }

    @Override
    public WeighInViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weighin_cardview_widget, parent, false);
        WeighInViewHolder wvh = new WeighInViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WeighInViewHolder holder, int position) {
        Log.d("recycler", "pos" + Integer.toString(position));
        int positionInList = position;
        WeighIn posWeighin = listOfWeighIns.get(positionInList);
        holder.mTextView_secondaryTitle.setText(Float.toString(posWeighin.getWeight()) + " KG");
        holder.mTextView_subText.setText(posWeighin.getDate().toString());
        if (!posWeighin.getPicturePath().isEmpty() && posWeighin.getPicturePath() != null) {
            holder.mImageView.setImageBitmap(bmf.createBitmapFromFilePath(posWeighin.getPicturePath()));
        } else {
            holder.mImageView.setVisibility(View.GONE);
        }

        holder.mTextView_topTitle.setText("WEIGHIN #" + posWeighin.getId());


        setAnimation(holder.mCardView, position);

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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recView) {
        super.onAttachedToRecyclerView(recView);
    }

    @Override
    public int getItemCount() {
        return listOfWeighIns.size();
    }

    public static class WeighInViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTextView_topTitle;
        TextView mTextView_secondaryTitle;
        TextView mTextView_subText;
        TextView mTextView_startWeight;
        TextView mTextView_goalWeight;
        TextView mTextView_percentageText;
        ImageView mImageView;
        SeekBar mSeekBar;

        WeighInViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTextView_topTitle = (TextView) itemView.findViewById(R.id.cardView_topTitle);
            mTextView_secondaryTitle = (TextView) itemView.findViewById(R.id.cardView_secondaryTitle);
            mTextView_subText = (TextView) itemView.findViewById(R.id.cardView_subText);



            mImageView = (ImageView) itemView.findViewById(R.id.cardView_image);

        }

    }

}
