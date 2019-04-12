package com.chata.chata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class RoletyParterActivity extends BaseRoletyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolety_parter);

        Initialize();
    }

    @Override
    protected Map<Integer, String> getButtonsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(R.id.buttonSalon1Up, "salon1_up");
        map.put(R.id.buttonSalon1Down, "salon1_down");
        map.put(R.id.buttonSalon2Up, "salon2_up");
        map.put(R.id.buttonSalon2Down, "salon2_down");
        map.put(R.id.buttonJadalniaUp, "jadalnia_up");
        map.put(R.id.buttonJadalniaDown, "jadalnia_down");
        map.put(R.id.buttonKotlowniaUp, "kotlownia_up");
        map.put(R.id.buttonKotlowniaDown, "kotlownia_down");
        map.put(R.id.buttonKuchniaUp, "kuchnia_up");
        map.put(R.id.buttonKuchniaDown, "kuchnia_down");
        map.put(R.id.buttonGabinetUp, "gabinet_up");
        map.put(R.id.buttonGabinetDown, "gabinet_down");
        map.put(R.id.buttonWiatrolapUp, "garderoba_up");
        map.put(R.id.buttonWiatrolapDown, "garderoba_down");
        return map;
    }

    @Override
    protected String getHost() {
        return "192.168.1.26";
    }
}
