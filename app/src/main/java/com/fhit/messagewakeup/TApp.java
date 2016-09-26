package com.fhit.messagewakeup;

import android.app.Application;

import org.xutils.x;

/**
 * Created by liubaoxing on 2016/9/21 15:12<br/>
 * Email:liubaoxinggo@foxmail.com<br/>
 */
public class TApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
