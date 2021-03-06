package com.githhub.nathankuhn.chip8;

public class Memory {

    public static final int MEMORY_SIZE = 4096;

    private byte[] memoryValues;

    public Memory() {
        memoryValues = new byte[MEMORY_SIZE];
    }

    public void loadROM(int initialAddress, ROM rom) {
        System.arraycopy(rom.content, 0, memoryValues, initialAddress, rom.length);
    }

    public byte getMemoryValue(int address) {
        return memoryValues[address];
    }

    public void setMemoryValue(int address, byte value) {
        memoryValues[address] = value;
    }

}
