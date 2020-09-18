package com.chata.chata.scheduling;

import android.content.Context;
import android.widget.Toast;

import com.chata.chata.Area;
import com.chata.chata.R;
import com.chata.chata.Utility;

import java.util.ArrayList;

public class ScheduleCommunication {
    static Utility utility = Utility.getObject();
    private final Context applicationContext;

    public ScheduleCommunication(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ArrayList<ScheduleEntity> retrieveData() {
        ArrayList<ScheduleEntity> entities = new ArrayList<>();

        getScheduledEvents(entities, applicationContext.getString(R.string.basement_host), Area.GROUNDFLOOR, Area.ATTIC);
        getScheduledEvents(entities, applicationContext.getString(R.string.attic_host), Area.ATTIC, Area.GROUNDFLOOR);

        return entities;
    }

    private void getScheduledEvents(ArrayList<ScheduleEntity> entities, String baseUrl, Area lightsArea, Area blindsArea) {
        String response = utility.Get("http://" + baseUrl + "/getScheduledEvents");

        if (utility.isGetResponseSuccessful(response)) {
            if (!response.isEmpty()) {
                String[] lines = response.split(";\r\n");
                int i = entities.size();
                for (String line : lines) {
                    entities.add(ScheduleEntityHelpers.fromHttpResponseLine(i++, line, lightsArea, blindsArea));
                }
            }
        } else {
            Toast.makeText(applicationContext, "Nie udało się połączyć z rozdzielnią", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean sendData(ArrayList<ScheduleEntity> entities) {
        ScheduleEntity[] basementEntities = entities.stream()
                .filter(x -> (x.getType() == ScheduleType.LIGHTS && x.getArea() == Area.GROUNDFLOOR) ||
                (x.getType() == ScheduleType.BLINDS && x.getArea() == Area.ATTIC))
                .toArray(ScheduleEntity[]::new);
        ScheduleEntity[] atticEntities = entities.stream()
                .filter(x -> (x.getType() == ScheduleType.LIGHTS && x.getArea() == Area.ATTIC) ||
                (x.getType() == ScheduleType.BLINDS && x.getArea() == Area.GROUNDFLOOR))
                .toArray(ScheduleEntity[]::new);

        int clearBasementResponseCode = clear(applicationContext.getString(R.string.basement_host));
        int clearAtticResponseCode = clear(applicationContext.getString(R.string.attic_host));
        boolean basementSend = false, atticSend = false;

        if (clearBasementResponseCode == 200)
            basementSend = send(basementEntities, applicationContext.getString(R.string.basement_host));

        if (clearAtticResponseCode == 200)
            atticSend = send(atticEntities, applicationContext.getString(R.string.attic_host));

        return basementSend && atticSend;
    }

    private int clear(String baseUrl) {
        return utility.Post("http://" + baseUrl + "/clearSchedule", "");
    }

    private boolean send(ScheduleEntity[] entities, String baseUrl) {
        int i = 0;
        String requestBody = "";

        for (ScheduleEntity entity: entities) {
            String line = ScheduleEntityHelpers.buildHttpRequestLine(entity);
            requestBody += line;
            i++;

            if (i == 10) {
                if (utility.Post("http://" + baseUrl + "/schedule", requestBody) != 200)
                    return false;

                i = 0;
                requestBody = "";
            }
        }

        if (!requestBody.isEmpty()) {
            if (utility.Post("http://" + baseUrl + "/schedule", requestBody) != 200)
                return false;
        }

        return true;
    }
}
