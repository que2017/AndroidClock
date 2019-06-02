package com.duiyi.androidclock.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duiyi.androidclock.R;

import java.util.Calendar;

/**
 * 时钟界面的布局
 *
 * @author zhang
 * @since 2019/6/2
 */
public class TimeView extends LinearLayout {
    private static final String TAG = TimeView.class.getSimpleName();

    private TextView mTime;

    @SuppressLint("HandlerLeak")
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshTime();
            if (getVisibility() == View.VISIBLE) {
                timeHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DefaultLocale")
    private void refreshTime() {
        Calendar cale = Calendar.getInstance();
        mTime.setText(String.format("%s:%s:%s", twoDigit(cale.get(Calendar.HOUR_OF_DAY)),
                twoDigit(cale.get(Calendar.MINUTE)),
                twoDigit(cale.get(Calendar.SECOND))));
        Log.i(TAG, "refresh time");
    }

    private String twoDigit(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == View.VISIBLE) {
            Log.i(TAG, "onVisibilityChanged: " + visibility);
            timeHandler.sendEmptyMessage(0);
        } else {
            Log.i(TAG, "onVisibilityChanged: " + visibility);
            timeHandler.removeMessages(0);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTime = findViewById(R.id.tvTime);
        timeHandler.sendEmptyMessage(0);
    }
}
