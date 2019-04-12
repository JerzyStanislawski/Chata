package com.chata.chata;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.Map;

public abstract class BaseRoletyActivity extends BasePageActivity {

    protected Map<Integer, String> buttons;

    protected void Initialize() {
        buttons = getButtonsList();

        for (final Integer s : buttons.keySet()) {
            Button sButton = findViewById(s);
            sButton.setOnClickListener(new CompoundButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utility.Post("http://" + getHost() + "/impulsRolety", buttons.get(s));
                }
            });
        }
    }

    protected abstract Map<Integer, String> getButtonsList();
    protected abstract String getHost();
}
