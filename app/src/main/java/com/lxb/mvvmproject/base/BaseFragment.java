package com.lxb.mvvmproject.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxb.mvvmproject.action.ClickAction;
import com.lxb.mvvmproject.action.ToastAction;
import com.lxb.mvvmproject.util.annotations.InjectManager;


/**
 * "浪小白" 创建 2019/8/13.
 * 界面名称以及功能:
 */
public abstract class BaseFragment<VM extends AndroidViewModel, SV extends ViewDataBinding> extends Fragment implements ToastAction, ClickAction {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ARouter.getInstance().inject(this);
        bindingView = DataBindingUtil.inflate(inflater, InjectManager.inject(this), null, false);
        return bindingView.getRoot();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog(String... tips) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showDialog(tips);
        }
    }

    /**
     *
     */
    @Override
    public <V extends View> V findViewById(int id) {
        if (getActivity() != null)
            return getActivity().findViewById(id);
        else
            return null;
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideDialog();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bindingView != null) {
            bindingView.unbind();
            bindingView = null;
        }
    }

    /**
     * Fragment 返回键被按下时回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件，回传给 Activity
        return false;
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

    public abstract void initView();

}
