package com.chata.chata;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Data {

    static Map<Integer, Light> basementLights = new HashMap<>();
    static Map<Integer, Light> atticLights = new HashMap<>();
    static Map<Integer, Blind> basementBlinds = new HashMap<>();
    static Map<Integer, Blind> atticBlinds = new HashMap<>();
    static Map<String, String> lightNames = new HashMap<>();
    static Map<String, String> blindNames = new HashMap<>();

    static {
        InitializeBasementLights();
        InitializeAtticLights();
        InitializeBasementBlinds();
        InitializeAtticBlinds();
        InitializeLightsMap();
        InitializeBlindsMap();
    }

    private static void InitializeBlindsMap() {
        getBlindNames().putAll(basementBlinds.values().stream().collect(Collectors.toMap(Blind::getName, Blind::getNiceName)));
        getBlindNames().putAll(atticBlinds.values().stream().collect(Collectors.toMap(Blind::getName, Blind::getNiceName)));
    }

    private static void InitializeLightsMap() {
        getLightNames().putAll(basementLights.values().stream().collect(Collectors.toMap(Light::getName, Light::getNiceName)));
        getLightNames().putAll(atticLights.values().stream().collect(Collectors.toMap(Light::getName, Light::getNiceName)));
    }

    private static void InitializeAtticBlinds() {
        atticBlinds.put(R.id.buttonAdam1Up, new Blind("pokoj1_1_up", "Adam lewe"));
        atticBlinds.put(R.id.buttonAdam1Down, new Blind("pokoj1_1_down", "Adam lewe"));
        atticBlinds.put(R.id.buttonAdam2Up, new Blind("pokoj1_2_up", "Adam prawe"));
        atticBlinds.put(R.id.buttonAdam2Down, new Blind("pokoj1_2_down", "Adam prawe"));
        atticBlinds.put(R.id.buttonUlaUp, new Blind("pokoj2_up", "Ula"));
        atticBlinds.put(R.id.buttonUlaDown, new Blind("pokoj2_down", "Ula"));
        atticBlinds.put(R.id.buttonSypialniaUp, new Blind("sypialnia_up", "Sypialnia"));
        atticBlinds.put(R.id.buttonSypialniaDown, new Blind("sypialnia_down", "Sypialnia"));
    }

    private static void InitializeBasementBlinds() {
        basementBlinds.put(R.id.buttonSalon1Up, new Blind("salon1_up", "Salon - taras"));
        basementBlinds.put(R.id.buttonSalon1Down, new Blind("salon1_down", "Salon - taras"));
        basementBlinds.put(R.id.buttonSalon2Up, new Blind("salon2_up", "Salon - ogród"));
        basementBlinds.put(R.id.buttonSalon2Down, new Blind("salon2_down", "Salon - ogród"));
        basementBlinds.put(R.id.buttonJadalniaUp, new Blind("jadalnia_up", "Jadalnia"));
        basementBlinds.put(R.id.buttonJadalniaDown, new Blind("jadalnia_down", "Jadalnia"));
        basementBlinds.put(R.id.buttonKotlowniaUp, new Blind("kotlownia_up", "Kotłownia"));
        basementBlinds.put(R.id.buttonKotlowniaDown, new Blind("kotlownia_down", "Kotłownia"));
        basementBlinds.put(R.id.buttonKuchniaUp, new Blind("kuchnia_up", "Kuchnia"));
        basementBlinds.put(R.id.buttonKuchniaDown, new Blind("kuchnia_down", "Kuchnia"));
        basementBlinds.put(R.id.buttonGabinetUp, new Blind("gabinet_up", "Gabinet"));
        basementBlinds.put(R.id.buttonGabinetDown, new Blind("gabinet_down", "Gabinet"));
        basementBlinds.put(R.id.buttonWiatrolapUp, new Blind("garderoba_up", "Wiatrołap"));
        basementBlinds.put(R.id.buttonWiatrolapDown, new Blind("garderoba_down", "Wiatrołap"));
    }

    private static void InitializeAtticLights() {
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

        atticLights.put(R.id.switchSypialnia, new Light("sypialnia", OSW_SYPIALNIA, "Sypialnia"));
        atticLights.put(R.id.switchAdam, new Light("pokoj1", OSW_POKOJ_ADAM, "Adam"));
        atticLights.put(R.id.switchUla, new Light("pokoj2", OSW_POKOJ_ULA, "Ula"));
        atticLights.put(R.id.switchLazienka, new Light("lazienka_gora", OSW_LAZIENKA_GORA, "Łazienka - główne"));
        atticLights.put(R.id.switchLazienkaKinkiety, new Light("lazienka_kinkiety", OSW_LAZIENKA_KINKIETY, "Łazienka - kinkiety"));
        atticLights.put(R.id.switchLazienkaLed, new Light("lazienka_led", OSW_LAZIENKA_LED, "Łazienka - LED"));
        atticLights.put(R.id.switchGarderoba, new Light("garderoba_gora", OSW_GARDEROBA_GORA, "Garderoba"));
        atticLights.put(R.id.switchPralnia, new Light("pralnia", OSW_PRALNIA, "Pralnia"));
        atticLights.put(R.id.switchKorytarz, new Light("korytarz", OSW_KORYTARZ, "Korytarz"));
        atticLights.put(R.id.switchKorytarzLed, new Light("korytarz_nocne", OSW_KORYTARZ_NOCNE, "Korytarz - nocne"));
        atticLights.put(R.id.switchSchody, new Light("schody", OSW_SCHODY, "Schody"));
        atticLights.put(R.id.switchSchodyLed, new Light("schody_led", OSW_SCHODY_LED, "Schody - nocne"));
    }

    private static void InitializeBasementLights() {
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

        basementLights.put(R.id.switchSalon, new Light("salon", OSW_SALON, "Salon - główne"));
        basementLights.put(R.id.switchSalonKominek, new Light("salon_kominek", OSW_SALON_KOMINEK, "Salon - kominek"));
        basementLights.put(R.id.switchSalonLed,  new Light("salon_led", OSW_SALON_LED, "Salon - LED"));
        basementLights.put(R.id.switchKuchnia, new Light("kuchnia", OSW_KUCHNIA, "Kuchnia - główne"));
        basementLights.put(R.id.switchSzafki, new Light("kuchnia_szafki", OSW_KUCHNIA_SZAFKI, "Kuchnia - blat"));
        basementLights.put(R.id.switchSpizarnia, new Light("spizarnia", OSW_SPIZARNIA, "Spiżarnia"));
        basementLights.put(R.id.switchJadalnia, new Light("jadalnia", OSW_JADALNIA, "Jadalnia"));
        basementLights.put(R.id.switchBarek, new Light("barek", OSW_BAREK, "Barek"));
        basementLights.put(R.id.switchGabinet, new Light("gabinet", OSW_GABINET, "Gabinet"));
        basementLights.put(R.id.switchLazienka, new Light("lazienka", OSW_LAZIENKA, "Łazienka - główne"));
        basementLights.put(R.id.switchLustro, new Light("lazienka_lustro", OSW_LAZIENKA_LUSTRO, "Łazienka - lustro"));
        basementLights.put(R.id.switchHall, new Light("hall", OSW_KORYTARZ, "Hall"));
        basementLights.put(R.id.switchWiatrolap, new Light("wiatrolap", OSW_WIATROLAP, "Wiatrołap"));
        basementLights.put(R.id.switchKotlownia, new Light("kotlownia", OSW_KOTLOWNIA, "Kotłownia"));
        basementLights.put(R.id.switchGaraz, new Light("garaz", OSW_GARAZ, "Garaż"));
        basementLights.put(R.id.switchWejscie, new Light("wejscie", OSW_WEJSCIE_GLOWNE, "Wejście"));
    }

    public static Map<Integer, Light> getBasementLightsData() {
        return basementLights;
    }

    public static Map<Integer, Light> getAtticLightsData() {
        return atticLights;
    }

    public static Map<Integer, Blind> getBasementBlindsData() {
        return basementBlinds;
    }

    public static Map<Integer, Blind> getAtticBlindsData() {
        return atticBlinds;
    }

    public static Map<String, String> getLightNames() { return lightNames; }

    public static Map<String, String> getBlindNames() { return blindNames; }
}
