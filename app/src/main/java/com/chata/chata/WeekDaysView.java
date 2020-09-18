package com.chata.chata;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class WeekDaysView {

    protected View view;

    public WeekDaysView(View view) {
        this.view = view;
    }

    public ArrayList<DayOfWeek> getSelectedDays() {
        ArrayList<DayOfWeek> days = new ArrayList<>();

        CheckToggleDayButton(R.id.btn_monday, days, DayOfWeek.MONDAY);
        CheckToggleDayButton(R.id.btn_tuesday, days, DayOfWeek.TUESDAY);
        CheckToggleDayButton(R.id.btn_wednesday, days, DayOfWeek.WEDNESDAY);
        CheckToggleDayButton(R.id.btn_thursday, days, DayOfWeek.THURSDAY);
        CheckToggleDayButton(R.id.btn_friday, days, DayOfWeek.FRIDAY);
        CheckToggleDayButton(R.id.btn_saturday, days, DayOfWeek.SATURDAY);
        CheckToggleDayButton(R.id.btn_sunday, days, DayOfWeek.SUNDAY);

        return days;
    }

    private void CheckToggleDayButton(int buttonId, ArrayList<DayOfWeek> days, DayOfWeek dayOfWeek) {
        ToggleButton toggleButton = view.findViewById(buttonId);
        if (toggleButton.isChecked()) {
            days.add(dayOfWeek);
        }
    }

    public void setDays(ArrayList<DayOfWeek> days) {
        LinearLayout layout = (LinearLayout)view;
        final int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = layout.getChildAt(i);
            if (v instanceof ToggleButton) {
                ToggleButton button = (ToggleButton)v;
                switch (v.getId()) {
                    case R.id.btn_monday:
                        button.setChecked(days.contains(DayOfWeek.MONDAY));
                        break;
                    case R.id.btn_tuesday:
                        button.setChecked(days.contains(DayOfWeek.TUESDAY));
                        break;
                    case R.id.btn_wednesday:
                        button.setChecked(days.contains(DayOfWeek.WEDNESDAY));
                        break;
                    case R.id.btn_thursday:
                        button.setChecked(days.contains(DayOfWeek.THURSDAY));
                        break;
                    case R.id.btn_friday:
                        button.setChecked(days.contains(DayOfWeek.FRIDAY));
                        break;
                    case R.id.btn_saturday:
                        button.setChecked(days.contains(DayOfWeek.SATURDAY));
                        break;
                    case R.id.btn_sunday:
                        button.setChecked(days.contains(DayOfWeek.SUNDAY));
                        break;
                    default:
                        break;
                }

            }
        }
    }
}
