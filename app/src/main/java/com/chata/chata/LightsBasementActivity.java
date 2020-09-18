package com.chata.chata;

import android.os.Bundle;

import java.util.Map;

public class LightsBasementActivity extends BaseLightsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_basement);

        Initialize();
    }

    @Override
    protected Map<Integer, Light> getSwitchList() {
        return Data.getBasementLightsData();
    }

    @Override
    protected String getHost() {
        return getString(R.string.basement_host);
    }
}