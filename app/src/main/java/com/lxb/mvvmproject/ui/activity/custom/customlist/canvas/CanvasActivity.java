package com.lxb.mvvmproject.ui.activity.custom.customlist.canvas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.databinding.ActivityCanvasBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;

/**
 * 自定义画板
 */
@ContentView(R.layout.activity_canvas)
public class CanvasActivity extends BaseActivity<NoViewModel, ActivityCanvasBinding> {


    public static void start(Context context) {
        Intent intent = new Intent(context, CanvasActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        bindingView.include.tvTitle.setText("自定义控件画板");
        bindingView.btnClear.setOnClickListener(v -> bindingView.icvFirst.clear());
        bindingView.btnSave.setOnClickListener(v -> {
            toast("暂不提供保存图片的功能");
        });
    }
}
