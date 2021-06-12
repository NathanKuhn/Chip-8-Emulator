package com.githhub.nathankuhn.chip8;

public class ChipState {

    private final int INITIAL_INSTRUCTION_ADDRESS = 0x000;
    private final int INITIAL_STACK_ADDRESS = 0xEA0;

    private Memory memory;
    private Registers registers;
    private Timers timers;
    private int stackAddress;
    private int instructionAddress;

    public ChipState() {

        memory = new Memory();
        registers = new Registers();
        timers = new Timers();
        instructionAddress = INITIAL_INSTRUCTION_ADDRESS;
        stackAddress = INITIAL_STACK_ADDRESS;



    }

}
