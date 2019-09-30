package com.lxb.mvvmproject.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * "浪小白" 创建 2019/3/6.
 * 界面名称以及功能: 图片压缩
 */

public class ImageFileUtil {
    /**
     * 质量压缩
     * 质量压缩并不会改变图片在内存中的大小，仅仅会减小图片所占用的磁盘空间的大小，因为质量压缩不会改变图片的分辨率，
     * 而图片在内存中的大小是根据width*height*一个像素的所占用的字节数计算的，宽高没变，在内存中占用的大小自然不会变，
     * 质量压缩的原理是通过改变图片的位深和透明度来减小图片占用的磁盘空间大小，所以不适合作为缩略图，可以用于想保持图片质量的同时减小图片所占用的磁盘空间大小。
     * 另外，由于png是无损压缩，所以设置quality无效，以下是实现方式：
     *
     * @param format  图片格式 jpeg,png,webp
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    public static void compress(String file, Bitmap.CompressFormat format, int quality) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, file);
        Bitmap originBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        originBitmap.compress(format, quality, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "resultImg.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 采样率压缩
     * 采样率压缩是通过设置BitmapFactory.Options.inSampleSize，来减小图片的分辨率，进而减小图片所占用的磁盘空间和内存大小。
     * 设置的inSampleSize会导致压缩的图片的宽高都为1/inSampleSize，整体大小变为原始图片的inSampleSize平方分之一，当然，这些有些注意点：
     * 1、inSampleSize小于等于1会按照1处理
     * 2、inSampleSize只能设置为2的平方，不是2的平方则最终会减小到最近的2的平方数，如设置7会按4进行压缩，设置15会按8进行压缩。 
     * 具体的代码实现方式如下
     *
     * @param inSampleSize 可以根据需求计算出合理的inSampleSize
     */
    public static void compress(String file, int inSampleSize, int reqWidth, int reqHeight) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, file);
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置此参数是仅仅读取图片的宽高到options中，不会将整张图片读到内存中，防止oom
        options.inJustDecodeBounds = true;
        if (reqWidth != 0 || reqHeight != 0) {
            final int width = options.outWidth;
            final int height = options.outHeight;
            if (height > reqHeight || width > reqWidth) {
                //计算图片高度和我们需要高度的最接近比例值
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                //宽度比例值
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                //取比例值中的较大值作为inSampleSize
                inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
            }
        }
        Bitmap emptyBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap resultBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "resultImg.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过减少图片的像素来降低图片的磁盘空间大小和内存大小，可以用于缓存缩略图
     *
     * @param file
     * @param v
     */
    public void compress(String file, View v) {
        File sdFile = Environment.getExternalStorageDirectory();
        File originFile = new File(sdFile, file);
        Bitmap bitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        //设置缩放比
        int radio = 8;
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / radio, bitmap.getHeight() / radio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        RectF rectF = new RectF(0, 0, bitmap.getWidth() / radio, bitmap.getHeight() / radio);
        //将原图画在缩放之后的矩形上
        canvas.drawBitmap(bitmap, null, rectF, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(sdFile, "sizeCompress.jpg"));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
