package com.grace.www.qrcode.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.grace.www.qrcode.camera.CameraManager;
import com.grace.www.qrcode.decode.RGBLuminanceSource;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;

public class Util {

    public static Activity currentActivity = null;

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getWindowWidthPix() {
        int ver = Build.VERSION.SDK_INT;
        Display display = currentActivity.getWindowManager()
                .getDefaultDisplay();
        int width = 0;
        if (ver < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            width = dm.widthPixels;
        } else {
            Point point = new Point();
            display.getSize(point);
            width = point.x;
        }
        return width;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getWindowHeightPix() {
        int ver = Build.VERSION.SDK_INT;
        Display display = currentActivity.getWindowManager()
                .getDefaultDisplay();
        int height = 0;
        if (ver < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            height = dm.heightPixels;
        } else {
            Point point = new Point();
            display.getSize(point);
            height = point.y;
        }
        return height;
    }

    /**
     * 给定一张带有二维码的图片的Uri，识别其中的二维码
     * 目前这个方法，当图片是截图产生的平整的图片，是可以识别的，但是如果是拍照的二维码，暂时识别不了
     *
     * @param context
     * @param uri     相册照片的Uri
     * @return
     */
    public static Result scanningImage(Activity context, Uri uri) {
        if (uri == null || uri.equals("")) {
            return null;
        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        try {


//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true; // 先获取原大小
//
//            Bitmap scanBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),null,options);
//
//            options.inJustDecodeBounds = false; // 获取新的大小
//
//            Log.e("cccheight",options.outHeight+"/"+options.outWidth);
//            int sampleSize = (int) (options.outHeight / (float) 2000);
//            Log.e("cccheight=======",sampleSize+"/");
//
//            if (sampleSize <= 0)
//                sampleSize = 1;
//
//            sampleSize=5;
//            options.inSampleSize = sampleSize;
//             scanBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),null,options);


            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            Log.e("cv",bitmap.getWidth()+"?/"+bitmap.getHeight());
            Bitmap scanBitmap = getSmallerBitmap(bitmap);


            RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
            QRCodeReader reader = new QRCodeReader();
            return reader.decode(bitmap1, hints);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 输入文字，生成转换后的二维码图片
     * 内部可设置生成二维码的宽高
     *
     * @param content
     * @return
     */
    public static Bitmap createQRCode(String content) {
        int QR_WIDTH = 100;
        int QR_HEIGHT = 100;

        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();
            if (content == null || "".equals(content) || content.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // cheng chen de er wei ma
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过设置Camera打开闪光灯
     */
    public static void turnLightOn() {
        CameraManager.get().turnLightOn();
    }


    /**
     * 通过设置Camera关闭闪光灯
     */
    public static void turnLightOff() {
        CameraManager.get().turnLightOff();

    }


    public static String recode(String str) {
        String format = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                format = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234      ISO8859-1", format);
            } else {
                format = str;
                Log.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 一定要转换为小图，不然摄像头拍的照片很大，直接oom
     * https://blog.csdn.net/qq_28057541/article/details/52119896
     * 这也是仅仅适用于这个二维码占据照片篇幅较大的情况，当一张图分辨率很大，但是其中的二维码占据一小部分时，为了不oom就要把大照片缩小
     * 但是缩小后，二维码就更小了，就识别不出来
     * @param bitmap
     * @return
     */
    public static Bitmap getSmallerBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() / 160000;
        if (size <= 1) return bitmap; // 如果小于
        else {
            Matrix matrix = new Matrix();
            matrix.postScale((float) (1 / Math.sqrt(size)), (float) (1 / Math.sqrt(size)));
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizeBitmap;
        }
    }

}
