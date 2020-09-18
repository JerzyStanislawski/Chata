package com.chata.chata.scheduling;

import android.content.Intent;

import com.chata.chata.ArduinoResponseParser;
import com.chata.chata.Area;
import com.chata.chata.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ScheduleEntityHelpers {

    public static ScheduleEntity createFromIntent(Intent data) {

        ScheduleType scheduleType = (ScheduleType) data.getSerializableExtra("scheduleType");
        String room = data.getStringExtra("room");
        String roomNiceName = data.getStringExtra("roomNiceName");
        int hour = data.getIntExtra("hour", 0);
        int minute = data.getIntExtra("minute", 0);
        int daysMask = data.getIntExtra("daysMask",255);
        Boolean onOrUp = data.getBooleanExtra("onOrUp", false);
        Area area = (Area) data.getSerializableExtra("area");
        int id = data.getIntExtra("id", 0);

        return new ScheduleEntity(id, scheduleType, room, roomNiceName, LocalTime.of(hour, minute), daysMask, onOrUp, area);
    }

    public static void fillIntent(ScheduleEntity entity, Intent intent) {
        intent.putExtra("scheduleType", entity.getType());
        intent.putExtra("room", entity.getRoom());
        intent.putExtra("roomNiceName", entity.getRoomNiceName());
        intent.putExtra("hour", entity.getTime().getHour());
        intent.putExtra("minute",  entity.getTime().getMinute());
        intent.putExtra("daysMask",  entity.getDaysMask());
        intent.putExtra("onOrUp",  entity.getOnOrUp());
        intent.putExtra("area", entity.getArea());
        if (entity.getId() != null)
            intent.putExtra("id", entity.getId());
    }

    public static ScheduleEntity fromHttpResponseLine(int id, String line, Area lightArea, Area blindArea) {
        String[] parts = line.split(",");

        ScheduleType type = parts[1].equals("0") ? ScheduleType.LIGHTS : ScheduleType.BLINDS;
        String niceName = type == ScheduleType.LIGHTS ? Data.getLightNames().get(parts[0]) : Data.getBlindNames().get(parts[0]);
        LocalTime time = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("H:m"));
        int daysMask = Integer.parseInt(parts[3]);
        boolean onOrUp = parts[4].equals("1");
        Area area = type == ScheduleType.LIGHTS ? lightArea : blindArea;

        return new ScheduleEntity(id, type, parts[0], niceName, time, daysMask, onOrUp, area);
    }

    public static String buildHttpRequestLine(ScheduleEntity entity) {
        return String.format("%s,%c,%02d:%02d,%03d,%d;",
                    entity.getRoom(),
                    entity.getType() == ScheduleType.LIGHTS ? 'L' : 'B',
                    entity.getTime().getHour(),
                    entity.getTime().getMinute(),
                    entity.getDaysMask(),
                    entity.getOnOrUp() ? 1 : 0);
    }
}
