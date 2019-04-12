package com.chata.chata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class RoletyPoddaszeActivity extends BaseRoletyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolety_poddasze);

        Initialize();
    }


    @Override
    protected Map<Integer, String> getButtonsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(R.id.buttonAdam1Up, "pokoj1_1_up");
        map.put(R.id.buttonAdam1Down, "pokoj1_1_down");
        map.put(R.id.buttonAdam2Up, "pokoj1_2_up");
        map.put(R.id.buttonAdam2Down, "pokoj1_2_down");
        map.put(R.id.buttonUlaUp, "pokoj2_up");
        map.put(R.id.buttonUlaDown, "pokoj2_down");
        map.put(R.id.buttonSypialniaUp, "sypialnia_up");
        map.put(R.id.buttonSypialniaDown, "sypialnia_down");
        return map;
    }

    @Override
    protected String getHost() {
        return "192.168.1.25";
    }
}
