package com.chata.chata;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class OswietlenieParterActivity extends BaseOswietlenieActivity {

    final int OSW_GARAZ = 24;
    final int OSW_KOTLOWNIA = 25;
    final int OSW_WIATROLAP = 26;
    final int OSW_KORYTARZ = 28;
    final int OSW_SALON_LED = 30;
    final int OSW_JADALNIA = 31;
    final int OSW_KUCHNIA = 32;
    final int OSW_BAREK = 33;
    final int OSW_SPIZARNIA = 34;
    final int OSW_GABINET = 35;
    final int OSW_LAZIENKA = 36;
    final int OSW_WEJSCIE_GLOWNE = 37;
    final int OSW_WEJSCIE_KOLUMNA = 38;
    final int OSW_FRONT = 39;
    final int OSW_SALON_KOMINEK = 40;
    final int OSW_LAZIENKA_LUSTRO = 41;
    final int OSW_SALON = 42;
    final int OSW_KUCHNIA_SZAFKI = 43;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oswietlenie_parter);

        Initialize();
    }

    @Override
    protected Map<Integer, Light> getSwitchList() {
        Map<Integer, Light> map = new HashMap<Integer, Light>();
        map.put(R.id.switchSalon, new Light("salon", OSW_SALON));
        map.put(R.id.switchSalonKominek, new Light("salon_kominek", OSW_SALON_KOMINEK));
        map.put(R.id.switchSalonLed,  new Light("salon_led", OSW_SALON_LED));
        map.put(R.id.switchKuchnia, new Light("kuchnia", OSW_KUCHNIA));
        map.put(R.id.switchSzafki, new Light("kuchnia_szafki", OSW_KUCHNIA_SZAFKI));
        map.put(R.id.switchSpizarnia, new Light("spizarnia", OSW_SPIZARNIA));
        map.put(R.id.switchJadalnia, new Light("jadalnia", OSW_JADALNIA));
        map.put(R.id.switchBarek, new Light("barek", OSW_BAREK));
        map.put(R.id.switchGabinet, new Light("gabinet", OSW_GABINET));
        map.put(R.id.switchLazienka, new Light("lazienka", OSW_LAZIENKA));
        map.put(R.id.switchLustro, new Light("lazienka_lustro", OSW_LAZIENKA_LUSTRO));
        map.put(R.id.switchHall, new Light("hall", OSW_KORYTARZ));
        map.put(R.id.switchWiatrolap, new Light("wiatrolap", OSW_WIATROLAP));
        map.put(R.id.switchKotlownia, new Light("kotlownia", OSW_KOTLOWNIA));
        map.put(R.id.switchGaraz, new Light("garaz", OSW_GARAZ));
        map.put(R.id.switchWejscie, new Light("wejscie", OSW_WEJSCIE_GLOWNE));
        return map;
    }

    @Override
    protected String getHost() {
        return "192.168.1.25";
    }
}

