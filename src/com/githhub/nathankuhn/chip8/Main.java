package com.githhub.nathankuhn.chip8;

public class Main {

    public static void main(String[] args) {

        ROM testROM = new ROM(new int[] {
                0x22, 0x0a, // 0x200 | Call 0x20a
                0x60, 0x0b, // 0x202 | V0 = 0x0b
                0x30, 0x0b, // 0x204 | if (V0 != 0x0b)
                0x64, 0x0b, // 0x206 | V4 = 0x0b
                0x00, 0x00, // 0x208 | exit

                0x61, 0x0a, // 0x20a | V1 = 0x0a
                0x62, 0x02, // 0x20c | V2 = 0x02
                0x63, 0x03, // 0x20e | V3 = 0x03
                0x00, 0xEE  // 0x210 | return
        });

        Memory mem = new Memory();
        mem.loadROM(0x200, testROM);

        Cpu cpu = new Cpu(mem);
        cpu.run();

    }
}
