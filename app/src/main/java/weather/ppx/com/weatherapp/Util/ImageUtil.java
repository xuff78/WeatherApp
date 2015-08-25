package weather.ppx.com.weatherapp.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtil {

    public static Bitmap resizeToXY(Bitmap bm, int x, int y) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = (float)x/ width;
        float scaleHeight = (float)y/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

}
