package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by BetaStåhl on 1/12/2016.
 */
public class VisualProgressFragment extends Fragment {
    GridView gv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.visual_progress_fragment, container, false);

        gv = (GridView)view.findViewById(R.id.grid_view);
        gv.setAdapter(new ImageAdapter(getActivity()));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "" + i,
                        Toast.LENGTH_SHORT).show();

            }
        });




        return view;
    }
}
