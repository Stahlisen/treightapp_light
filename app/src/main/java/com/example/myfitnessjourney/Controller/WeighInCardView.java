package com.example.myfitnessjourney.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Model.WeighIn;
import Services.WeighInLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeighInCardView extends Fragment {



    public WeighInCardView() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("recycler", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weighins_recyclerview, container, false);
        Context applicationContext = getActivity();
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.recyclerView);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(applicationContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        WeighInLab.get(getActivity()).getAllWeighIns(getActivity());
        List<WeighIn> weighinList = WeighInLab.get(getActivity()).getWeighins();
        RecyclerViewAdapter rva = new RecyclerViewAdapter(weighinList, getActivity(), "WeighIns");
        recList.setAdapter(rva);

        return v;
    }
    /*
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private CardView mCardView;

        public RecyclerViewHolder (final View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mTextView = (TextView) view.findViewById(R.id.cardView_title);
            mCardView = (CardView) view.findViewById(R.id.card_view);
            mCardView.setRadius(15);
        }

        public TextView getTextView() {
            return mTextView;
        }

    }
    */


}
