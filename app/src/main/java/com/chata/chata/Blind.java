package com.chata.chata;

public class Blind {

    private String name;
    private String niceName;

    public Blind(String name, String niceName) {
        this.name = name;
        this.niceName = niceName;
    }

    public String getName() {
        return name;
    }

    public String getNiceName() {
        return niceName;
    }

    @Override
    public String toString() {
        return this.niceName;
    }
}
