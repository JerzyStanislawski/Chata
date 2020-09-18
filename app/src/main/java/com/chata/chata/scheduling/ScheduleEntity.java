package com.chata.chata.scheduling;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import com.chata.chata.Area;

public class ScheduleEntity {

    private final Integer id;
    private final String room;
    private final String roomNiceName;
    private final ScheduleType type;
    private final LocalTime time;
    private int daysMask;
    private final Boolean onOrUp;
    private final Area area;

    public ScheduleEntity(Integer id, ScheduleType type, String room, String roomNiceName, LocalTime time, int daysMask, boolean onOrUp, Area area) {
        this.id = id;
        this.type = type;
        this.room = room;
        this.time = time;
        this.daysMask = daysMask;
        this.onOrUp = onOrUp;
        this.area = area;
        this.roomNiceName = roomNiceName;
    }

    public String getRoom() {
        return room;
    }

    public String getRoomNiceName() { return roomNiceName; }

    public ScheduleType getType() {
        return type;
    }

    public LocalTime getTime() {
        return time;
    }

    public Boolean getOnOrUp() {
        return onOrUp;
    }

    public Area getArea() {
        return area;
    }

    public int getDaysMask() { return daysMask; }

    public Integer getId() { return id; }
}
