package com.githhub.nathankuhn.chip8;

public class Cpu {

    private static final int MAXIMUM_STEPS = 100;
    private static final int INITIAL_INSTRUCTION_ADDRESS = 0x200;
    private static final int INITIAL_STACK_ADDRESS = 0xEA0;

    private Memory memory;
    private Registers registers;
    private Timers timers;
    private int stackAddress;
    private int instructionAddress;

    public Cpu(Memory memory) {
        this.memory = memory;
        registers = new Registers();
        timers = new Timers();
        instructionAddress = INITIAL_INSTRUCTION_ADDRESS;
        stackAddress = INITIAL_STACK_ADDRESS;
    }

    public void run() {
        String instruction = "";
        int steps = 0;
        while (!instruction.equals("0000") && steps < MAXIMUM_STEPS) {
            instruction = fetchInstruction();
            handleInstruction(instruction);
            steps++;
        }

        System.out.println(registers.getDataRegisterValue(10));
        System.out.println(registers.getDataRegisterValue(11));
        System.out.println(registers.getDataRegisterValue(12));
    }

    private String fetchInstruction() {
        byte byte1 = memory.getMemoryValue(instructionAddress);
        byte byte2 = memory.getMemoryValue(instructionAddress + 1);

        String instruction = String.format("0x%02x", byte1) + String.format("0x%02x", byte2);
        instruction = instruction.replaceAll("0x", "");
        instructionAddress += 2;

        return instruction;
    }

    private void handleInstruction(String instruction) {

        switch (instruction.charAt(0)) {
            case ('6'):
                int register = Integer.parseInt(instruction.substring(1, 2), 16);
                int value = Integer.parseInt(instruction.substring(2, 4), 16);
                registers.setDataRegisterValue(register, (byte) value);
                break;
            case ('b'):
                instructionAddress = Integer.parseInt(instruction.substring(1), 16);
                break;
            default:
                break;
        }

    }

}
