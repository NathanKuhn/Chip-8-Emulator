package com.githhub.nathankuhn.chip8;

public class Main {

    public static void main(String[] args) {

        byte[] testROM = {
                0x22, 0x08,
                0x60, 0x0b,
                0x64, 0x0b,
                0x00, 0x00,
                0x61, 0x0a,
                0x62, 0x02,
                0x63, 0x03,
                0x00, (byte)0xEE
        };

        Memory mem = new Memory();
        mem.loadROM(0x200, testROM);

        Cpu cpu = new Cpu(mem);
        cpu.run();

    }
}
