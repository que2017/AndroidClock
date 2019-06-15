package com.duiyi.androidclock.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.duiyi.androidclock.R;

import java.util.Date;

/**
 * 闹钟视图类
 *
 * @author zhang
 * @since 2019/6/5
 */
public class AlarmView extends LinearLayout {
    private static final String TAG = AlarmView.class.getSimpleName();

    private ListView mAlarmListView;

    private Button mAddAlarm;

    private ArrayAdapter<AlarmData> mAdapter;

    public AlarmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AlarmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mAlarmListView = findViewById(R.id.alarmList);
        mAddAlarm = findViewById(R.id.addAlarm);

        mAdapter = new ArrayAdapter<AlarmData>(getContext(), android.R.layout.simple_list_item_1);
        mAlarmListView.setAdapter(mAdapter);

        mAdapter.add(new AlarmData(System.currentTimeMillis()));

        mAddAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 添加闹钟
            }
        });
    }

    private static class AlarmData {
        private String timeLabel;

        private long time;

        private Date date;

        public AlarmData(long time) {
            this.time = time;
            date = new Date(this.time);
            timeLabel = date.getHours() + ":" + date.getMinutes();
        }

        public long getTime() {
            return time;
        }

        public String getTimeLabel() {
            return timeLabel;
        }

        @Override
        public String toString() {
            return getTimeLabel();
        }
    }
}
