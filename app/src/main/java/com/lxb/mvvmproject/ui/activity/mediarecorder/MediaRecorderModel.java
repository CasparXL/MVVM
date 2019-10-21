package com.lxb.mvvmproject.ui.activity.mediarecorder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.lxb.mvvmproject.base.BaseViewModel;
import com.lxb.mvvmproject.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class MediaRecorderModel extends BaseViewModel {


    public MediaRecorderModel(@NonNull Application application) {
        super(application);
    }

    public void startRecord() {

    }

    public void stopRecord() {

    }

}
