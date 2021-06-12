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
        registers.print();
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

        byte byte1;
        byte byte2;

        switch (instruction.charAt(0)) {
            case ('0'):
                 switch( instruction.substring(1)) {
                     case ("0e0"):
                         break; // TODO clear the screen
                     case ("0ee"):
                         stackAddress -= 2;
                         assert (stackAddress > INITIAL_STACK_ADDRESS) : "No routine to return from";
                         byte1 = memory.getMemoryValue(stackAddress);
                         byte2 = memory.getMemoryValue(stackAddress + 1);
                         memory.setMemoryValue(stackAddress, (byte)0);
                         memory.setMemoryValue(stackAddress + 1, (byte)1);
                         instructionAddress = ((byte1 & 0xff) << 8) | (byte2 & 0xff);
                         break;
                     default:
                         byte1 = (byte) (instructionAddress >> 8);
                         byte2 = (byte) (instructionAddress & 0xff);
                         memory.setMemoryValue(stackAddress, byte1);
                         memory.setMemoryValue(stackAddress + 1, byte2);
                         stackAddress += 2;
                         assert (stackAddress < Memory.MEMORY_SIZE);
                         instructionAddress = Integer.parseInt(instruction.substring(1, 3), 16);
                         break;
                 }
                break;

            case ('1'):
                instructionAddress = Integer.parseInt(instruction.substring(1), 16);
                break;

            case ('2'):
                byte1 = (byte) (instructionAddress >> 8);
                byte2 = (byte) (instructionAddress & 0xff);
                memory.setMemoryValue(stackAddress, byte1);
                memory.setMemoryValue(stackAddress + 1, byte2);
                stackAddress += 2;
                assert (stackAddress < Memory.MEMORY_SIZE);
                instructionAddress = Integer.parseInt(instruction.substring(1, 4), 16);
                break;

            case ('3'):
                break;

            case ('4'):
                break;

            case ('5'):
                break;

            case ('6'):
                int register = Integer.parseInt(instruction.substring(1, 2), 16);
                int value = Integer.parseInt(instruction.substring(2, 4), 16);
                registers.setDataRegisterValue(register, (byte) value);
                break;

            case ('7'):
                break;

            case ('8'):
                break;

            case ('9'):
                break;

            case ('a'):
                break;

            case ('b'):
                instructionAddress = Integer.parseInt(instruction.substring(1), 16) + registers.getDataRegisterValue(0);
                break;

            case ('c'):
                break;

            case ('d'):
                break;

            case ('e'):
                break;

            case ('f'):
                break;

            default:
                break;
        }

    }

}
