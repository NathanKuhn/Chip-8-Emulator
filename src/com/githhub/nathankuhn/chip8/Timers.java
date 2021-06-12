package com.githhub.nathankuhn.chip8;

public class Timers {

    private int delayTimer;
    private int soundTimer;

    public Timers() {
        delayTimer = 0;
        soundTimer = 0;
    }

    public void update() {
        if (delayTimer > 0)
            delayTimer--;
        if (soundTimer > 0)
            soundTimer--;
    }

    public int getDelayTimer() {
        return delayTimer;
    }
    public int getSoundTimer() {
        return soundTimer;
    }

    public void setDelayTimer(int value) {
        delayTimer = value;
    }
    public void setSoundTimer(int value) {
        soundTimer = value;
    }

}
