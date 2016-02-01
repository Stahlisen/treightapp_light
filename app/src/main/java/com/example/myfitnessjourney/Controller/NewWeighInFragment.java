package com.example.myfitnessjourney.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import Model.WeighIn;
import Services.BitMapFactory;
import Services.WeighInLab;

/**
 * Created by fredrikstahl on 15-07-29.
 */
public class NewWeighInFragment extends Fragment {
    private TextView mEditWeight;
    private TextView mEditPicture;
    private ImageButton mChooseWeight;
    private ImageButton mTakePicture;
    private ImageButton mBrowsePicture;
    private ImageView mPhotoView;
    private int selectedConstant;
    private float selectedFloat;
    private boolean didSetWeight = false;
    private static final int CAMERA_CAPTURE_REQUEST = 1111;
    private static final int IMAGE_PICK_REQUEST = 2222;
    public String imgPath;
    private Menu mMenu;
    public static float ENTERED_WEIGHT;
    public static Bitmap ENTERED_PHOTO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        populateViewForOrientation(inflater, frameLayout);
        //View view = inflater.inflate(R.layout.fragment_new_weighin, parent, false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);

        return frameLayout;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu = menu;
        inflater.inflate(R.menu.new_weighin, menu);

        if (ENTERED_WEIGHT > 0.0f) {
            menu.findItem(R.id.action_save_weighin).setEnabled(true);
        } else {

            menu.findItem(R.id.action_save_weighin).setEnabled(false);
        }
    }

    //Handle actionbar button clicks and save action
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                ENTERED_WEIGHT = 0;
                ((MainActivity) getActivity()).customNavigationCall(R.id.home, null);
                return true;

            case R.id.action_save_weighin:
                if (imgPath != null) {
                    WeighIn weighin = getWeighInData();
                    weighin.setPicturePath(imgPath);

                    WeighInLab.get(getActivity()).createNewRealmWeighIn(weighin, getActivity().getApplicationContext());
                    ENTERED_WEIGHT = 0;
                    ENTERED_PHOTO = null;
                    ((MainActivity) getActivity()).customNavigationCall(R.id.home, null);

                } else {
                    WeighInLab.get(getActivity()).createNewRealmWeighIn(getWeighInData(), getActivity().getApplicationContext());
                    ENTERED_WEIGHT = 0;
                    ((MainActivity) getActivity()).customNavigationCall(R.id.home, null);
                }
                return true;
        }
        return false;
    }

    private void populateViewForOrientation (LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View view = inflater.inflate(R.layout.fragment_new_weighin, viewGroup);

        //Initialize all view elements
        mEditWeight = (TextView) view.findViewById(R.id.weight_edit);
        mEditPicture = (TextView) view.findViewById(R.id.picture_edit);
        mChooseWeight = (ImageButton) view.findViewById(R.id.weight_edit_button);
        mTakePicture = (ImageButton) view.findViewById(R.id.take_picture_button);
        mBrowsePicture = (ImageButton) view.findViewById(R.id.browse_gallery_image_button);
        mPhotoView = (ImageView) view.findViewById(R.id.photo_view);

        //Create listeners
        createListeners();

        if (ENTERED_WEIGHT > 0.0f) {
            String ENTERED_WEIGHT_String = Float.toString(ENTERED_WEIGHT);
            mEditWeight.setText(ENTERED_WEIGHT_String + " KG");
        }

        if (ENTERED_PHOTO != null) {
            mPhotoView.setImageBitmap(ENTERED_PHOTO);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());

    }



    public void createListeners() {

        mChooseWeight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showWeightEditor();

            }
        });

        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        mBrowsePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
    }
    /*
    Intent som meddelar att öppna upp "ta foto" activity
     */

    public void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAMERA_CAPTURE_REQUEST);
    }

    /*
    "Välj foto"-funktionen är just nu satt som disabled då vidare utveckling måste göras för att
    kunna använda externa galleri (Dropbox, google photos etc).
     */

    public void pickPhoto() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), IMAGE_PICK_REQUEST);

    }

    //Metod för att hantera resultatet av fototagningen samt olika scenarion.

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {

            if (requestCode == CAMERA_CAPTURE_REQUEST) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                ENTERED_PHOTO = bp;
                mPhotoView.setImageBitmap(bp);
                String imagePathForWeighIn = savePicture(bp);
                imgPath = imagePathForWeighIn;
            }

            if (requestCode ==  IMAGE_PICK_REQUEST) {
                Uri selectedImageUri = data.getData();
                imgPath = getPath(selectedImageUri);
                if (selectedImageUri != null) {
                    BitMapFactory bmf = new BitMapFactory();
                    mPhotoView.setImageBitmap(bmf.createBitmapFromFilePath(imgPath));
                }
            }
        }

    }

    //Get the image path from the data returned from taken photo

    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


    //Save the image with date as filename
    public String savePicture(Bitmap bmp) {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        FileOutputStream out = null;
        String filename = "weighin_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFile = new File(path, filename + ".jpg");

        try
        {
            out = new FileOutputStream(imageFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return imageFile.toString();
    }

    //Show a dialog which lets the user enter a weight and handle button clicks on the dialog
    public void showWeightEditor() {
        final Dialog d = new Dialog(getActivity());
            d.setContentView(R.layout.weight_picker_dialog);
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            WindowManager.LayoutParams params = d.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height =  WindowManager.LayoutParams.MATCH_PARENT;
            d.getWindow().setAttributes(params);
        }

        Button button_cancel = (Button) d.findViewById(R.id.button_cancel);
        Button button_set = (Button) d.findViewById(R.id.button_set);
        final NumberPicker np_constant = (NumberPicker) d.findViewById(R.id.numberPicker_constant);
        final NumberPicker np_decimal = (NumberPicker) d.findViewById(R.id.numberPicker_decimal);

        //Config numberpicker for constant
        np_constant.setMaxValue(500); // max value 100
        np_constant.setMinValue(0);   // min value 0
        np_constant.setWrapSelectorWheel(false);
        np_constant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldValb, int newValb) {
                selectedConstant = picker.getValue();
                String s = Integer.toString(picker.getValue());
            }

        });

        final String[] decimals = {".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"};

        //Config numberpicker for decimals
        np_decimal.setMaxValue(decimals.length - 1); // max value 100
        np_decimal.setMinValue(0);   // min value 0
        np_decimal.setWrapSelectorWheel(false);
        np_decimal.setDisplayedValues(decimals);
        np_decimal.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldValb, int newValb) {
                int index = picker.getValue();
                String val = decimals[index];
                selectedFloat = Float.parseFloat(val);

            }

        });

        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float tempfloat = new Float(selectedConstant);
                ENTERED_WEIGHT = tempfloat + selectedFloat;
                String s = Float.toString(ENTERED_WEIGHT);
                mEditWeight.setText(s + "KG");
                mEditWeight.setTextColor(getResources().getColor(R.color.green_2));
                isDidSetWeight(true);
                d.dismiss();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        if (didSetWeight) {
            np_constant.setValue(selectedConstant);
            String s = Float.toString(selectedFloat);
            int index = Arrays.asList(decimals).indexOf(s);
            np_decimal.setValue(index);

        }

        d.show();

    }

    //Make sure we enable save button if any weight was entered
    public void isDidSetWeight(boolean is) {
        didSetWeight = is;

        if (is) {

            mMenu.findItem(R.id.action_save_weighin).setEnabled(true);
        } else {
            mMenu.findItem(R.id.action_save_weighin).setEnabled(false);
        }
    }

    //Method to get the data entered
    public WeighIn getWeighInData() {
        WeighIn weighin = new WeighIn();
        weighin.setDate(new Date());
        weighin.setWeight(ENTERED_WEIGHT);
        return weighin;
    }

    //Save data on configuration change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("lastEnteredWeight", ENTERED_WEIGHT);
        outState.putParcelable("lastEnteredPhoto", ENTERED_PHOTO);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle("New Weigh In");
    }


}

