package com.chata.chata;

import android.os.Bundle;

import java.util.Map;

public class BlindsAtticActivity extends BaseBlindsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinds_attic);

        Initialize();
    }


    @Override
    protected Map<Integer, Blind> getButtonsList() {
        return Data.getAtticBlindsData();
    }

    @Override
    protected String getHost() {
        return getString(R.string.basement_host);
    }
}
