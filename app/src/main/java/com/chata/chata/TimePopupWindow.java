package com.chata.chata;

import android.view.View;
import android.widget.TimePicker;

import java.time.LocalTime;

public class TimePopupWindow extends WeekDaysView {

    private View popupView;

    public TimePopupWindow(View popupView) {
        super(popupView.findViewById(R.id.popup_week_days));
        this.popupView = popupView;
    }

    public LocalTime getTime() {
        TimePicker timePicker = popupView.findViewById(R.id.popupTimePicker);
        return LocalTime.of(timePicker.getHour(), timePicker.getMinute());
    }

    public void init() {
        TimePicker timePicker = popupView.findViewById(R.id.popupTimePicker);
        timePicker.setIs24HourView(true);
        timePicker.setHour(7);
        timePicker.setMinute(0);
    }
}
