package com.chata.chata;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

public abstract class BaseBlindsActivity extends BasePageActivity {

    protected Map<Integer, Blind> buttons;

    protected void Initialize() {
        buttons = getButtonsList();

        for (final Integer s : buttons.keySet()) {
            Button sButton = findViewById(s);
            sButton.setOnClickListener(v -> {
                int responseCode = utility.Post("http://" + getHost() + "/impulsRolety", buttons.get(s).getName());
                if (responseCode != 200) {
                    Toast.makeText(this.getApplicationContext(), "Odpowiedź ze skrzynki: " + Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected abstract Map<Integer, Blind> getButtonsList();
    protected abstract String getHost();
}
