package com.chata.chata;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class OswietleniePoddaszeActivity extends BaseOswietlenieActivity {

    final int NUMER_DOMU = 22;
final int OSW_KORYTARZ_NOCNE = 30;
final int OSW_SYPIALNIA = 24;
final int OSW_POKOJ_ADAM = 25;
final int OSW_POKOJ_ULA = 26;
final int OSW_SCHODY_LED = 27;
final int ROL_GARDEROBA_DOWN = 28;
final int OSW_SCHODY = 29;
final int OSW_TARAS = 34;
final int OSW_LAZIENKA_GORA = 31;
final int OSW_TARAS_PUNKTOWE = 36;
final int OSW_LAZIENKA_KINKIETY = 33;
final int OSW_TARAS_KOLUMNA = 23; 
final int OSW_PRALNIA = 35;
final int OSW_LAZIENKA_LED = 32;
final int OSW_GARDEROBA_GORA = 37;
    final int OSW_KORYTARZ = 53;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oswietlenie_poddasze);

        Initialize();
    }


    @Override
    protected Map<Integer, Light> getSwitchList() {
        Map<Integer, Light> map = new HashMap<Integer, Light>();
        map.put(R.id.switchSypialnia, new Light("sypialnia", OSW_SYPIALNIA));
        map.put(R.id.switchAdam, new Light("pokoj1", OSW_POKOJ_ADAM));
        map.put(R.id.switchUla, new Light("pokoj2", OSW_POKOJ_ULA));
        map.put(R.id.switchLazienka, new Light("lazienka_gora", OSW_LAZIENKA_GORA));
        map.put(R.id.switchLazienkaKinkiety, new Light("lazienka_kinkiety", OSW_LAZIENKA_KINKIETY));
        map.put(R.id.switchLazienkaLed, new Light("lazienka_led", OSW_LAZIENKA_LED));
        map.put(R.id.switchGarderoba, new Light("garderoba_gora", OSW_GARDEROBA_GORA));
        map.put(R.id.switchPralnia, new Light("pralnia", OSW_PRALNIA));
        map.put(R.id.switchKorytarz, new Light("korytarz", OSW_KORYTARZ));
        map.put(R.id.switchKorytarzLed, new Light("korytarz_nocne", OSW_KORYTARZ_NOCNE));
        map.put(R.id.switchSchody, new Light("schody", OSW_SCHODY));
        map.put(R.id.switchSchodyLed, new Light("schody_led", OSW_SCHODY_LED));
        return map;
    }

    @Override
    protected String getHost() {
        return "192.168.1.26";
    }
}