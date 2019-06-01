package com.duiyi.androidclock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * 时钟程序的主入口
 *
 * @author zhang
 * @since 2019/6/1
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tabTime));
        mTabHost.addTab(mTabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm));
        mTabHost.addTab(mTabHost.newTabSpec("tabTimer").setIndicator("计时器").setContent(R.id.tabTimer));
        mTabHost.addTab(mTabHost.newTabSpec("tabStopWatch").setIndicator("秒表").setContent(R.id.tabStopWatch));
    }
}
