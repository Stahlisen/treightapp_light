package Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by fredrikstahl on 15-08-04.
 * This class creates a bitmap from a filepath passed as parameter to the constructor
 */
public class BitMapFactory {

    public Bitmap createBitmapFromFilePath(String filePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }
}
