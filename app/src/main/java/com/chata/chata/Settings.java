package com.chata.chata;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Settings {

    private LocalDateTime dateTime;
    private boolean holidayMode;
    private boolean twilightMode;
    private boolean morningMode;
    private LocalTime morningTime;
    private int morningDays;

    public Settings(LocalDateTime dateTime, boolean holidayMode, boolean twilightMode, boolean morningMode, LocalTime morningTime, int morningDays) {
        this.dateTime = dateTime;
        this.holidayMode = holidayMode;
        this.twilightMode = twilightMode;
        this.morningMode = morningMode;
        this.morningTime = morningTime;
        this.morningDays = morningDays;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isHolidayMode() {
        return holidayMode;
    }

    public boolean isTwilightMode() {
        return twilightMode;
    }

    public boolean isMorningMode() {
        return morningMode;
    }

    public LocalTime getMorningTime() {
        return morningTime;
    }

    public int getMorningDays() { return morningDays; }
}
