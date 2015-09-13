package Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by fredrikstahl on 15-08-04.
 */
public class BitMapFactory {

    public Bitmap createBitmapFromFilePath(String filePath) {
        if (filePath != null) {
            Log.d("picturePath", "onCreateBitmap: " + filePath);
        } else {
            Log.d("picturePath", "onCreateBitmap: null");
        }


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }
}
