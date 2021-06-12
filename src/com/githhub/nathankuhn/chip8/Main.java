package com.githhub.nathankuhn.chip8;

public class Main {

    public static void main(String[] args) {

        byte[] testROM = {
                0x6A, 0x0a,
                0x6B, 0x02,
                0x6C, 0x03,
                (byte)0xB2, 0x00
        };

        Memory mem = new Memory();
        mem.loadROM(0x200, testROM);

        Cpu cpu = new Cpu(mem);
        cpu.run();

    }
}
