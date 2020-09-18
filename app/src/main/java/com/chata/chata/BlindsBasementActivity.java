package com.chata.chata;

import android.os.Bundle;

import java.util.Map;

public class BlindsBasementActivity extends BaseBlindsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinds_basement);

        Initialize();
    }

    @Override
    protected Map<Integer, Blind> getButtonsList() {
        return Data.getBasementBlindsData();
    }

    @Override
    protected String getHost() {
        return getString(R.string.attic_host);
    }
}
