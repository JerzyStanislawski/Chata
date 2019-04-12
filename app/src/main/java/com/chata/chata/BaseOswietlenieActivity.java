package com.chata.chata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseOswietlenieActivity extends BasePageActivity {

    protected Map<Integer, Light> switches;
    boolean updatingStatus = false;

    Handler handler = new Handler();
    final Runnable updateStateTask = new Runnable() {
        public void run() {
            UpdateState();
            handler.postDelayed(this, 3000);
        }
    };

    public void UpdateState() {
        try {
            utility.Get("http://" + getHost() + "/getStatus", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null && response.length() > 0) {
                        Map<Integer, Boolean> state = ParseStatus(response);
                        UpdateSwitches(state);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void UpdateSwitches(Map<Integer, Boolean> state) {
        updatingStatus = true;
        for (final Integer s: switches.keySet()) {
            Switch sButton = findViewById(s);

            int lightId = switches.get(s).Output;
            if (state.containsKey(lightId)) {
                Boolean turnedOn = state.get(lightId);
                if (sButton.isChecked() != turnedOn)
                    sButton.setChecked(turnedOn);
            }
        }
        updatingStatus = false;
    }

    public Map<Integer, Boolean> ParseStatus(String response) {
        Map<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();
        String[] lines = response.split("\n");
        for (String line : lines) {
            String[] lights = line.split("=");
            statusMap.put(Integer.parseInt(lights[0]), lights[1].trim().equals("1"));
        }
        return statusMap;
    }

    protected void Initialize() {
        switches = getSwitchList();

        UpdateState();

        for (final Integer s: switches.keySet()) {
            Switch sButton = findViewById(s);
            sButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton cb, boolean on){
                    if (updatingStatus)
                        return;

                    utility.Post("http://" + getHost() + "/impulsOswietlenie", switches.get(s).Name + "=" + cb.isChecked());
                }
            });
        }

        //handler.postDelayed(updateStateTask, 3000);
    }

    protected abstract Map<Integer, Light> getSwitchList();
    protected abstract String getHost();
}
