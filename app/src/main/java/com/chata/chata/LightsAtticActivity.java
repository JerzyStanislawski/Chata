package com.chata.chata;

import android.os.Bundle;

import java.util.Map;

public class LightsAtticActivity extends BaseLightsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_attic);

        Initialize();
    }

    @Override
    protected Map<Integer, Light> getSwitchList() {
       return Data.getAtticLightsData();
    }

    @Override
    protected String getHost() {
        return getString(R.string.attic_host);
    }
}