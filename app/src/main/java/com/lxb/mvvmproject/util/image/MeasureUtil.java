package com.lxb.mvvmproject.util.image;

import android.content.Context;

/**
 * 说明：度量工具类
 * 作者：刘婉
 * 添加时间：2017/3/22 13:08
 * 修改人：刘婉
 * 修改时间：2017/3/22 13:08
 */
public class MeasureUtil {

    /**
     * 说明：根据手机的分辨率将dp转成为px
     * 作者：刘婉
     * 添加时间：2017/3/22 13:08
     * 修改人：刘婉
     * 修改时间：2017/3/22 13:08
     */
    public static int dip2px(Context context, float dpValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
        return 0;
    }

    /**
     * 说明：根据手机的分辨率将sp转成为px
     * 作者：刘婉
     * 添加时间：2017/3/22 18:59
     * 修改人：刘婉
     * 修改时间：2017/3/22 18:59
     */
    public static int sp2px(Context context, float spValue) {
        if (context != null) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
        return 0;
    }

}