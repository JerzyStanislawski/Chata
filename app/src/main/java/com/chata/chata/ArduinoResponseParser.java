package com.chata.chata;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ArduinoResponseParser {

    public Map<Integer, Boolean> parseLightsStatus(String response) {
        Map<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();
        String[] lines = response.split("\n");
        for (String line : lines) {
            String[] lights = line.split("=");
            statusMap.put(Integer.parseInt(lights[0]), lights[1].trim().equals("1"));
        }
        return statusMap;
    }

    public Settings parseAllSettingsResponse(String response) {
        String[] lines = response.split("\r\n");

        String timeString = lines[0].substring("Time: ".length());
        String dateString = lines[1].substring("Date: ".length());
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-M-d")), LocalTime.parse(timeString, DateTimeFormatter.ofPattern("H:m:s")));

        boolean holidayMode = parseBoolValue(lines[2], "holidayMode");
        boolean twilightMode = parseBoolValue(lines[3], "twilightMode");
        boolean morningMode = parseBoolValue(lines[4], "morningMode");

        String morningDaysString = lines[5].substring("morningDays: ".length());
        int morningDaysMask = Integer.parseUnsignedInt(morningDaysString);

        String morningTimeString = lines[6].substring("morningTime: ".length());
        LocalTime morningTime = LocalTime.parse(morningTimeString, DateTimeFormatter.ofPattern("H:m"));

        return new Settings(dateTime, holidayMode, twilightMode, morningMode, morningTime, morningDaysMask);
    }

    private boolean parseBoolValue(String line, String fieldName) {
        String boolValueString = line.substring((fieldName + ": ").length()).trim();
        return boolValueString.equals("1");
    }
}
