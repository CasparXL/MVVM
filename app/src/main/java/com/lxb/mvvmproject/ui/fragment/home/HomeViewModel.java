package com.lxb.mvvmproject.ui.fragment.home;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.lxb.mvvmproject.base.BaseViewModel;
import com.lxb.mvvmproject.bean.UserBean;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.network.Api;
import com.lxb.mvvmproject.network.BaseObserver;
import com.lxb.mvvmproject.network.Resource;
import com.lxb.mvvmproject.network.RxSchedulers;
import com.lxb.mvvmproject.ui.activity.bluetooth.BlueToothActivity;
import com.lxb.mvvmproject.ui.activity.camera.CameraActivity;
import com.lxb.mvvmproject.ui.activity.crash.CrashActivity;
import com.lxb.mvvmproject.ui.activity.custom.CustomActivity;
import com.lxb.mvvmproject.ui.activity.fingerprint.FingerPrintActivity;
import com.lxb.mvvmproject.ui.activity.mediarecorder.MediaRecorderActivity;
import com.lxb.mvvmproject.ui.activity.photograph.PhotographActivity;
import com.lxb.mvvmproject.ui.activity.skeleton.SkeletonActivity;
import com.lxb.mvvmproject.util.permissions.OnPermission;
import com.lxb.mvvmproject.util.permissions.Permission;
import com.lxb.mvvmproject.util.permissions.XXPermissions;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class HomeViewModel extends BaseViewModel {
    //首页列表数据源
    public ObservableList<String> listMutableLiveData;
    //模拟接口数据
    private MutableLiveData<Resource<String>> mData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        listMutableLiveData = new ObservableArrayList<>();
        mData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<String>> getData() {
        return mData;
    }

    //初始化适配器数据
    public void initAdapter() {
        listMutableLiveData.addAll(Arrays.asList(Config.FUNCTION));
    }

    //模拟网络请求方法，直接调用即可
    public void http() {
        Api.getInstance().getApiService().getUserAgreement().compose(RxSchedulers.applySchedulers()).subscribe(new BaseObserver<Resource<String>>() {
            @Override
            public void onSuccess(Resource<String> s) {
                getData().postValue(s);
            }

            @Override
            public void onFail(Resource<String> e) {

            }


            @Override
            public void onSubscribes(Disposable e) {
                compositeDisposable.add(e);
            }
        });
        /*HashMap<String,Object> map=new HashMap<>();
        map.put("account","13138895762");
        map.put("password","1234567");
        Api.getInstance().getApiService().loginUser(PostJson.toRequestBody(GsonUtils.toJson(map))).compose(RxSchedulers.applySchedulers()).subscribe(new BaseObserver<Resource<UserBean>>() {
            @Override
            public void onSuccess(Resource<UserBean> s) {
                getData().postValue(s);
            }

            @Override
            public void onFail(Resource<UserBean> e) {
                getData().postValue(e);
            }

            @Override
            public void onSubscribes(Disposable e) {
                compositeDisposable.add(e);
            }
        });*/
    }

    /**
     * 界面跳转操作
     *
     * @param position 适配器第几条
     * @param context  上下文
     */
    public void start(int position, Activity context) {
        if (position == 0) {//捕获异常
            CrashActivity.start(context);
        } else if (position == 1) {//骨架屏幕
            SkeletonActivity.start(context);
        } else if (position == 2) {//骨架屏幕
            FingerPrintActivity.start(context);
        } else if (position == 3) {//骨架屏幕
            PhotographActivity.start(context);
        } else if (position == 4) {//拍照预览
            CameraActivity.start(context);
        } else if (position == 5) {//自定义控件入门
            CustomActivity.start(context);
        } else if (position == 6) {//蓝牙
            BlueToothActivity.start(context);
        } else if (position == 7) {//音乐播放器
            permissions(context);
        }
    }

    private void permissions(Activity activity) {
        XXPermissions.with(activity).constantRequest().permission(Permission.RECORD_AUDIO).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll)
                    MediaRecorderActivity.start(activity);
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }

}
