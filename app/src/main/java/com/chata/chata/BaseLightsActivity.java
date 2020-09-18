package com.chata.chata;

import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseLightsActivity extends BasePageActivity {

    protected Map<Integer, Light> switches;
    boolean updatingStatus = false;
    ArduinoResponseParser responseParser = new ArduinoResponseParser();

    public void updateState() {
        String response = utility.Get("http://" + getHost() + "/getStatus");
        if (utility.isGetResponseSuccessful(response)) {
            Map<Integer, Boolean> state = responseParser.parseLightsStatus(response);
            updateSwitches(state);
        } else {
            Toast.makeText(this.getApplicationContext(), "Nie udało się połączyć z rozdzielnią", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSwitches(Map<Integer, Boolean> state) {
        updatingStatus = true;
        for (final Integer s: switches.keySet()) {
            Switch sButton = findViewById(s);

            int lightId = switches.get(s).getOutput();
            if (state.containsKey(lightId)) {
                Boolean turnedOn = state.get(lightId);
                if (sButton.isChecked() != turnedOn)
                    sButton.setChecked(turnedOn);
            }
        }
        updatingStatus = false;
    }

    protected void Initialize() {
         switches = getSwitchList();

        updateState();

        for (final Integer s: switches.keySet()) {
            Switch sButton = findViewById(s);
            sButton.setOnCheckedChangeListener((cb, on) -> {
                if (updatingStatus)
                    return;

                int responseCode = utility.Post("http://" + getHost() + "/impulsOswietlenie", switches.get(s).getName() + "=" + cb.isChecked());
                if (responseCode != 200) {
                    Toast.makeText(this.getApplicationContext(), "Odpowiedź z rozdzielni: " + Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
                    cb.setChecked(!on);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateState();
    }

    protected abstract Map<Integer, Light> getSwitchList();
    protected abstract String getHost();
}
