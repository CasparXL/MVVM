package com.lxb.mvvmproject.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.lxb.mvvmproject.util.image.MeasureUtil;

/**
 * "浪小白" 创建 2019/10/17.
 * 界面名称以及功能: 入门级别自定义控件
 */
public class IntroductionCustomView extends View {

    /**
     * 1.创建一个画笔
     */
    private Paint mPaint;

    /**
     * 2.初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(Color.WHITE);
        //STROKE                //描边
        //FILL                  //填充
        //FILL_AND_STROKE       //描边加填充
        //设置画笔模式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽度为30px
        mPaint.setStrokeWidth(30f);
        mPaint.setTextSize(MeasureUtil.sp2px(getContext(), 20));

    }

    public IntroductionCustomView(Context context) {
        super(context);
    }

    public IntroductionCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public IntroductionCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IntroductionCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param canvas 自定义控件第一个回调方法 画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);//画笔的颜色
//        canvas.drawPoint(100, 50, mPaint);  //绘制一个点
        //绘制一组点，坐标一一对应
        /*canvas.drawPoints(new float[]{
                100, 200,
                100, 50,
                90, 40
        }, mPaint);*/
//        canvas.drawLines(new float[]{10,30,200,30},mPaint);//画线，x1，y1，x2，y2
//        canvas.drawText("熊浪",getMeasuredWidth()/2,getMeasuredHeight()/2,mPaint);
        int baseLineX = 0;
        int baseLineY = 200;
        //画基线
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, baseLineY, 2500, baseLineY, mPaint);
        //画文字
        mPaint.setColor(Color.WHITE);

        canvas.drawText("熊浪", baseLineX, baseLineY, mPaint);
    }
}
