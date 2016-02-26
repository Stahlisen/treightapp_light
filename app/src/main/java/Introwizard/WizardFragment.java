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
 * Created by fredrikstahl on 16-02-08.
 */
public class WizardFragment extends Fragment {
    private int mPage;
    private EditText mEditName;
    private TextView mName;

    public static WizardFragment newInstance(int page) {
        WizardFragment frag = new WizardFragment();
        Bundle b = new Bundle();
        b.putInt("lastPage", page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int mPage = getArguments().getInt("lastPage");
        int layoutResId;

        switch (mPage) {
            case 0:
                layoutResId = R.layout.wizard_fragment_1;
                break;
            case 1:
                layoutResId = R.layout.wizard_fragment_2;
                break;
            case 2:
                layoutResId = R.layout.wizard_fragment_2;
                break;

            default:
                layoutResId = R.layout.wizard_fragment_1;
        }

        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);


        view.setTag(mPage);

        return view;
    }
}
