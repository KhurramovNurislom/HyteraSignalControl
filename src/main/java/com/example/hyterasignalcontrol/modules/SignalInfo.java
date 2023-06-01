package com.example.hyterasignalcontrol.modules;

public class SignalInfo {
    private int amplitude;
    private int spaceTime;
    private int frequency;

    public int getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public int getSpaceTime() {
        return spaceTime;
    }

    public void setSpaceTime(int spaceTime) {
        this.spaceTime = spaceTime;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "SignalInfo{" +
                "amplitude=" + amplitude +
                ", spaceTime=" + spaceTime +
                ", frequency=" + frequency +
                '}';
    }
}
