package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fredrikstahl on 16-02-08.
 */
public class WizardFragment extends Fragment {
    private int mPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutResId;

        switch (mPage) {
            case 0:
                layoutResId = R.layout.wizard_fragment_1;
                break;

            default:
                layoutResId = R.layout.wizard_fragment_1;
        }

        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        view.setTag(mPage);

        return view;
    }
}
