package com.chata.chata;

public class Light {
    private String Name;
    private Integer Output;
    private String niceName;
    private boolean State;

    public Light(String name, Integer output, String niceName) {
        setName(name);
        setOutput(output);
        this.setNiceName(niceName);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getOutput() {
        return Output;
    }

    public void setOutput(Integer output) {
        Output = output;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    @Override
    public String toString() {
        return this.niceName;
    }
}
