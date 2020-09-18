package com.chata.chata;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class WeekDaysHelper {

    public static int getDaysMask(ArrayList<DayOfWeek> days) {
        int mask = 1;

        for (DayOfWeek weekDay : days) {
            mask = mask | (1 << ((weekDay.getValue() % 7) + 1));
        }

        return mask;
    }

    public static ArrayList<DayOfWeek> weekDaysFromMask(int weekDaysMask) {
        ArrayList<DayOfWeek> weekDays = new ArrayList<>();
        DayOfWeek initialDay = DayOfWeek.SATURDAY;
        for (int i = 1; i < 8; i++) {
            if (((weekDaysMask >> i) & 1) == 1) {
                weekDays.add(initialDay.plus(i));
            }
        }

        return weekDays;
    }
}
