package com.example.myfitnessjourney.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import Model.WeighIn;
import Services.BitMapFactory;
import Services.WeighInLab;

public class WeighInDetailActivity extends AppCompatActivity {

    private ImageView mPictureView;
    private TextView mWeight, mDate, mIdView;
    private int mId;
    private WeighIn selectedWeighIn;
    BitMapFactory bmf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weighin_detail);

        Intent intentFromMaster = getIntent();
        mId = intentFromMaster.getIntExtra("weighin_id", 0);
        String s = Integer.toString(mId);
        //Log.d("weighinid", s);

        selectedWeighIn = WeighInLab.get(this).getWeighInWithId(mId);
        String text = Integer.toString(selectedWeighIn.getId());
        Log.d("weighinid", text);

        bmf = new BitMapFactory();

        mPictureView = (ImageView)findViewById(R.id.detail_image);
        mPictureView.setImageBitmap(bmf.createBitmapFromFilePath(selectedWeighIn.getPicturePath()));

        mWeight = (TextView) findViewById(R.id.detail_weight);
        String weight_text = Float.toString(selectedWeighIn.getWeight()) + "KG";
        mWeight.setText(weight_text);
        mDate = (TextView) findViewById(R.id.detail_date);
        String date_text = selectedWeighIn.getDate().toString();
        mDate.setText(date_text);

        mIdView = (TextView) findViewById(R.id.detail_id);
        String id_text = "WEIGHIN #" + selectedWeighIn.getId();
        mIdView.setText(id_text);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weigh_in_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
