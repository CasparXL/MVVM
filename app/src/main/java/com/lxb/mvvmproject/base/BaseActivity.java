package com.lxb.mvvmproject.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.action.ClickAction;
import com.lxb.mvvmproject.action.ToastAction;
import com.lxb.mvvmproject.ui.dialog.WaitDialog;
import com.lxb.mvvmproject.util.annotations.InjectManager;

/**
 * "浪小白" 创建 2019/11/19.
 * 界面名称以及功能:基类BaseActivity,使用该基类要记得使用阿里的ARouter路由跳转以及本项目工具类InjectManager，在Activity上方加入注解@ContentView(R.layout.xxx),包名不要选错了
 */
public abstract class BaseActivity<VM extends AndroidViewModel, SV extends ViewDataBinding> extends AppCompatActivity implements ToastAction, ClickAction {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;
    /**
     * 加载对话框
     */
    private BaseDialog mBaseDialog;
    /**
     * 对话框数量
     */
    private int mDialogTotal;

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mBaseDialog != null && mBaseDialog.isShowing();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        if (mBaseDialog == null) {
            mBaseDialog = new WaitDialog.Builder(this)
                    .setCancelable(false)
                    .create();
        }
        if (!mBaseDialog.isShowing()) {
            mBaseDialog.show();
        }
        mDialogTotal++;
    }

    /**
     * 显示加载对话框
     */
    public void showDialog(String... tips) {
        if (mBaseDialog == null) {
            mBaseDialog = new WaitDialog.Builder(this)
                    .setMessage(tips[0])
                    .setCancelable(false)
                    .create();
        } else {
            if ((mBaseDialog.findViewById(R.id.tv_wait_message)) != null) {
                if (!((TextView) mBaseDialog.findViewById(R.id.tv_wait_message)).getText().equals(tips[0])) {
                    ((TextView) mBaseDialog.findViewById(R.id.tv_wait_message)).setText(tips[0]);
                }
            }
        }
        if (!mBaseDialog.isShowing()) {
            mBaseDialog.show();
        }
        mDialogTotal++;
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal == 1) {
            if (mBaseDialog != null && mBaseDialog.isShowing()) {
                mBaseDialog.dismiss();
            }
        }
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ARouter.getInstance().inject(this);
        bindingView = DataBindingUtil.setContentView(this, InjectManager.inject(this));
        //沉浸式
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true).init();
        initViewModel();
        initIntent();
        initView(savedInstanceState);
        if (setTitleBar() != null) {
            ImmersionBar.setTitleBar(this, setTitleBar());
        }
    }

    public View setTitleBar() {
        return null;
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

    //初始化获取Intent数据
    protected abstract void initIntent();

    //初始化视图
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        if (isShowDialog()) {
            mBaseDialog.dismiss();
        }
        mBaseDialog = null;
        if (bindingView != null) {
            bindingView.unbind();
            bindingView = null;
        }
        super.onDestroy();
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

    @Override
    public void finish() {
        hideSoftKeyboard();
        super.finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        hideSoftKeyboard();
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
