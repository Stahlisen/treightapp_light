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

    RecyclerViewAdapter(List<WeighIn> weighins, Context context) {
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

        if(statisticsAvailable()) {
            float firstWeighin = listOfWeighIns.get(0).getWeight();
            holder.mTextView_startWeight.setText(Float.toString(firstWeighin) + " KG");
            float goalweight = WeighInLab.get(mContext).getGoal().getWeight();
            holder.mTextView_goalWeight.setText(Float.toString(goalweight) + " KG");
            float diffBetweenFirstAndGoal = firstWeighin - goalweight;
            float diffBetweenFirstAndThis = firstWeighin - posWeighin.getWeight();
            float percentageProgress = diffBetweenFirstAndThis/diffBetweenFirstAndGoal;
            String log_percentage = Float.toString(percentageProgress*100);
            Log.d("recycler", log_percentage + "percentage");
            double doubleToCeil = percentageProgress*100;
            int progress = (int) Math.ceil(doubleToCeil);
            String progressPrecentage_string;
            if (progress < 0) {
                progressPrecentage_string = "0";
            } else {
                progressPrecentage_string = Integer.toString(progress);
            }

            holder.mSeekBar.setProgress(progress);
            holder.mTextView_percentageText.setText(progressPrecentage_string + "% OF GOAL ACHIEVED");

            //Ta första vikten - goal weight (92 - 85) = 7
            //ta 92 - denna vikt : 92-91,5 =
            // 0,5/7
        } else {
            holder.mSeekBar.setVisibility(View.GONE);
            holder.mTextView_percentageText.setVisibility(View.GONE);
            holder.mTextView_startWeight.setVisibility(View.GONE);
            holder.mTextView_goalWeight.setVisibility(View.GONE);

        }

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
            mTextView_startWeight = (TextView) itemView.findViewById(R.id.seekbar_startText);
            mTextView_goalWeight = (TextView) itemView.findViewById(R.id.seekbar_endText);
            mTextView_percentageText = (TextView) itemView.findViewById(R.id.cardView_percentageText);
            mImageView = (ImageView) itemView.findViewById(R.id.cardView_image);
            mSeekBar = (SeekBar) itemView.findViewById(R.id.cardView_seekbar);

        }

    }

    //Check if user has a goal and a previous weigh in so we can produce statistics, if not, hide statistics.
    public boolean statisticsAvailable() {

        /*Retrieve the goal and see if it exists, if doesn't, don't show any statistics and
        retrieve list of weighins, if it's empty, don't show any statistics
        */
        if (WeighInLab.get(mContext).getGoal() != null && WeighInLab.get(mContext).getAllWeighIns(mContext).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
