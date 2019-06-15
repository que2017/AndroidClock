package com.duiyi.androidclock.views;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import com.duiyi.androidclock.R;

import java.util.Calendar;
import java.util.Date;

/**
 * 闹钟视图类
 *
 * @author zhang
 * @since 2019/6/5
 */
public class AlarmView extends LinearLayout {
    private static final String TAG = AlarmView.class.getSimpleName();

    private static final int ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;

    private static final String KEY_ALARM_LIST = "alarm_list";

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

        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        mAlarmListView.setAdapter(mAdapter);
        readSavedAlarmLsit();

        mAddAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });
    }

    private void addAlarm() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cale = Calendar.getInstance();
                cale.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cale.set(Calendar.MINUTE, minute);

                Calendar currentTime = Calendar.getInstance();

                if (cale.getTimeInMillis() <= currentTime.getTimeInMillis()) {
                    cale.setTimeInMillis(cale.getTimeInMillis() + ONE_DAY_MILLIS);
                }

                mAdapter.add(new AlarmData(cale.getTimeInMillis()));
                saveAlarmList();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void saveAlarmList() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            AlarmData alarmData = mAdapter.getItem(i);
            if (alarmData != null) {
                sb.append(alarmData.getTime()).append(",");
            }
        }
        String content = sb.toString().substring(0, sb.length() - 1);
        Log.i(TAG, "alarm list content: " + content);
        editor.putString(KEY_ALARM_LIST, content);
        editor.apply();
    }

    private void readSavedAlarmLsit() {
        mAdapter.clear();
        SharedPreferences sp  = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
        String content = sp.getString(KEY_ALARM_LIST, null);
        if (content != null) {
            String[] timeStrings = content.split(",");
            for (String string : timeStrings) {
                try {
                    mAdapter.add(new AlarmData(Long.parseLong(string)));
                } catch (NumberFormatException e) {
                    Log.e(TAG, "time string is wrong");
                }
            }
        }
    }

    private static class AlarmData {
        private String timeLabel;

        private long time;

        private Calendar calendar;

        @SuppressLint("DefaultLocale")
        public AlarmData(long time) {
            this.time = time;
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(this.time);
            timeLabel = String.format("%d月%d日 %d:%d",
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE));
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
