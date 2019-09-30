package com.lxb.mvvmproject.base;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.lxb.mvvmproject.util.ActivityUtils;
import com.lxb.mvvmproject.util.annotations.InjectManager;

/**
 * "浪小白" 创建 2019/8/13.
 * 界面名称以及功能:
 */
public abstract class BaseActivity<VM extends AndroidViewModel, SV extends ViewDataBinding> extends AppCompatActivity {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, InjectManager.inject(this));

        //沉浸式
        if (ImmersionBar.hasNotchScreen(this)) {//如果有刘海屏则让布局不与状态栏重合，如果没有刘海屏则全屏布局
            ImmersionBar.with(this).statusBarDarkFont(true).fitsSystemWindows(true).statusBarDarkFont(true).keyboardEnable(true).init();
        } else {
            ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true).init();
        }
        initViewModel();
        initIntent();
        initView(savedInstanceState);
        ActivityUtils.getDefault().attach(this);
    }

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }
    protected void toast(String string){
        ToastUtils.show(string);
    }

    //初始化获取Intent数据
    protected abstract void initIntent();

    //初始化视图
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        ActivityUtils.getDefault().detach(this);
        super.onDestroy();
        if (bindingView != null) {
            bindingView.unbind();
            bindingView = null;
        }
    }

    /**
     * 重置App界面的字体大小，fontScale 值为 1 代表默认字体大小
     *
     * @return 重置继承该activity子类的文字大小，使它不受系统字体大小限制
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = 1;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
