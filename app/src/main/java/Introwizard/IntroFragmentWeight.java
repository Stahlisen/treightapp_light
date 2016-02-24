package Introwizard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfitnessjourney.Controller.R;


/**
 * Created by fredrikstahl on 16-02-15.
 */
public class IntroFragmentWeight extends Fragment {
    private TextView mNameText;
    private EditText mEditWeight;
    private float enteredWeight;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResId = R.layout.wizard_fragment_2;
        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        mNameText = (TextView)view.findViewById(R.id.title_name);
        mEditWeight = (EditText)view.findViewById(R.id.title_goalweight);

        mNameText.setText(getArguments().getString("entered_name"));


        return view;
    }



}
