package com.lxb.mvvmproject.ui;


import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.clj.fastble.BleManager;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.databinding.ActivityMainBinding;
import com.lxb.mvvmproject.ui.fragment.home.HomeFragment;
import com.lxb.mvvmproject.ui.fragment.mine.MineFragment;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.listener.PerfectClickListener;
import com.lxb.mvvmproject.view.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * app主页板块
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<NoViewModel, ActivityMainBinding> implements ViewPager.OnPageChangeListener {

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle a) {
        initContentFragment();
        bindingView.tvHome.setOnClickListener(perfectClickListener);
        bindingView.tvMine.setOnClickListener(perfectClickListener);

    }
    @Override
    protected void onStop() {
        super.onStop();
        BleManager.getInstance().destroy();
    }
    //设置点击事件
    PerfectClickListener perfectClickListener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.tv_home:
                    setCurrentItem(0);
                    break;
                case R.id.tv_mine:
                    setCurrentItem(1);
                    break;
                default:
                    setCurrentItem(0);
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //初始化主页
    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new MineFragment());
        // new DoubanFragment() 含有书籍但其Api已失效
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        bindingView.vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        bindingView.vpContent.setOffscreenPageLimit(2);
        bindingView.vpContent.addOnPageChangeListener(this);
        setCurrentItem(0);
    }


    /*******************************************切换ViewPager的实现类************************************************/
    /**
     * 切换页面
     *
     * @param position 分类角标
     */
    private void setCurrentItem(int position) {
        switch (position) {
            case 0:
                bindingView.tvHome.setTextColor(0xffD81B60);
                bindingView.tvMine.setTextColor(0xFF424242);
                break;
            case 1:
                bindingView.tvMine.setTextColor(0xffD81B60);
                bindingView.tvHome.setTextColor(0xFF424242);
                break;
            default:
                bindingView.tvHome.setTextColor(0xffD81B60);
                bindingView.tvMine.setTextColor(0xFF424242);
                break;
        }
        bindingView.vpContent.setCurrentItem(position, false);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                setCurrentItem(0);
                break;
            case 1:
                setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
