package com.lxb.mvvmproject.view.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * "浪小白" 创建 2019/10/17.
 * 界面名称以及功能:
 */
public class CanvasView extends View {
    private int view_width = 0;	//屏幕的宽度
    private int view_height = 0;	//屏幕的高度
    private float preX;	//起始点的x坐标值
    private float preY;//起始点的y坐标值
    private Path path;	//路径
    public Paint paint = null;	//画笔
    Bitmap cacheBitmap = null;// 定义一个内存中的图片，该图片将作为缓冲区
    Canvas cacheCanvas = null;// 定义cacheBitmap上的Canvas对象

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        initCanvas(context);
    }

    private void initCanvas(Context context) {
        view_width = context.getResources().getDisplayMetrics().widthPixels; // 获取屏幕的宽度
        view_height = context.getResources().getDisplayMetrics().heightPixels; // 获取屏幕的高度
        System.out.println(view_width + "*" + view_height);
        // 创建一个与该View相同大小的缓存区
        cacheBitmap = Bitmap.createBitmap(view_width, view_height,
                Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);// 在cacheCanvas上绘制cacheBitmap
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.BLACK); // 设置默认的画笔颜色
        // 设置画笔风格
        paint.setStyle(Paint.Style.STROKE);    //设置填充方式为描边
        paint.setStrokeJoin(Paint.Join.ROUND);        //设置笔刷的图形样式
        paint.setStrokeCap(Paint.Cap.ROUND);    //设置画笔转弯处的连接风格
        paint.setStrokeWidth(5); // 设置默认笔触的宽度为5像素
        paint.setAntiAlias(true); // 使用抗锯齿功能
        paint.setDither(true); // 使用抖动效果
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFFFFFFF);	//设置背景颜色
        Paint bmpPaint = new Paint();	//采用默认设置创建一个画笔
        canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint); //绘制cacheBitmap
        canvas.drawPath(path, paint);	//绘制路径
        canvas.save();	//保存canvas的状态
        //canvas.restore();	//恢复canvas之前保存的状态，防止保存后对canvas执行的操作对后续的绘制有影响
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取触摸事件的发生位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y); // 将绘图的起始点移到（x,y）坐标点的位置
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= 5 || dy >= 5) { // 判断是否在允许的范围内
                    path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint); //绘制路径
                path.reset();
                cacheCanvas.save();
                break;
        }
        invalidate();
        return true;		// 返回true表明处理方法已经处理该事件
    }


    public void clear() {
/*        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(50);	//设置笔触的宽度
        cacheCanvas.drawPaint(paint);*/
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        cacheCanvas.drawPaint(paint);
        invalidate();
    }

    public void back() {
        cacheCanvas.restore();
    }
    public void nothing() {
        cacheBitmap=null;
        // 创建一个与该View相同大小的缓存区
        cacheBitmap = Bitmap.createBitmap(view_width, view_height,
                Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);// 在cacheCanvas上绘制cacheBitmap
        invalidate();
    }

    public void save(int i,int j) {
        //int i=0;
        try {
            saveBitmap("DrawingPicture","DrawingPicture_"+i,j);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 保存绘制好的位图到APP目录下
    public void saveBitmap(String filesize,String fileName,int j) throws IOException {

        String directoryPath;
        directoryPath=getFilePath(getContext(),filesize,fileName,j);
        //directoryPath=getFilePath(getContext(),filesize,fileName,j);
        File file = new File(directoryPath );	//创建文件对象
        try {
            file.createNewFile();	//创建一个新文件
            FileOutputStream fileOS = new FileOutputStream(file);	//创建一个文件输出流对象
            cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOS);	//将绘图内容压缩为PNG格式输出到输出流对象中
            fileOS.flush();	//将缓冲区中的数据全部写出到输出流中
            fileOS.close();	//关闭文件输出流对象
            Toast.makeText(getContext(), "成功保存到"+directoryPath, Toast.LENGTH_SHORT).show();//垴村成功，提示保存的路径
        }catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();//保存失败，提示原因
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        getContext().sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
    }

    public static String getFilePath(Context context,String filesize,String dir,int j) {  //获取APP当前目录并且设置图片保存路径
        String directoryPath="";
        if(j==0) {
            //判断SD卡是否可用
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {
                directoryPath =context.getExternalFilesDir(filesize).getAbsolutePath()+File.separator+ dir + ".png";

                // directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
            }else{
                //没内存卡就存机身内存
                directoryPath=context.getFilesDir().getAbsolutePath()
                        +File.separator
                        +filesize+ File.separator
                        + dir + ".png";

                // directoryPath=context.getCacheDir()+File.separator+dir;
            }}

        else
        {
            directoryPath = Environment.getExternalStorageDirectory()
                    + File.separator +
                    Environment.DIRECTORY_DCIM
                    +File.separator+"Camera"+File.separator+dir + ".png";
            //	File file = new File(directoryPath);
            //if(!file.exists()){//判断文件目录是否存在
            //	file.mkdirs();
            //directoryPath=directoryPath+File.separator+dir + ".png";
        }
        //LogUtil.i("filePath====>"+directoryPath);
        //}
        return directoryPath;
    }

}
