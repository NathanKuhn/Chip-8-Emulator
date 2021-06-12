package com.githhub.nathankuhn.chip8;

public class Memory {

    public final int MEMORY_SIZE = 4096;

    private byte[] memoryValues;

    public Memory() {
        memoryValues = new byte[MEMORY_SIZE];
    }

    public byte getMemoryValue(int address) {
        return memoryValues[address];
    }

    public void setMemoryValue(int address, byte value) {
        memoryValues[address] = value;
    }

}
