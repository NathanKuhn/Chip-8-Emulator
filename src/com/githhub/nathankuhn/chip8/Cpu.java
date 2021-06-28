package com.githhub.nathankuhn.chip8;

import java.util.Random;

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

        int value;
        int register;
        int register1;
        int register2;

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
                register = Integer.parseInt(instruction.substring(1, 2), 16);
                byte1 = Byte.parseByte(instruction.substring(2, 4), 16);
                if (registers.getDataRegisterValue(register) == byte1)
                    instructionAddress += 2;
                break;

            case ('4'):
                register = Integer.parseInt(instruction.substring(1, 2), 16);
                byte1 = Byte.parseByte(instruction.substring(2, 4), 16);
                if (registers.getDataRegisterValue(register) != byte1)
                    instructionAddress += 2;
                break;

            case ('5'):
                register1 = Integer.parseInt(instruction.substring(1, 2), 16);
                register2 = Integer.parseInt(instruction.substring(2, 3), 16);
                if (registers.getDataRegisterValue(register1) == registers.getDataRegisterValue(register2))
                    instructionAddress += 2;
                break;

            case ('6'):
                register = Integer.parseInt(instruction.substring(1, 2), 16);
                value = Integer.parseInt(instruction.substring(2, 4), 16);
                registers.setDataRegisterValue(register, (byte) value);
                break;

            case ('7'):
                register = Integer.parseInt(instruction.substring(1, 2), 16);
                value = Integer.parseInt(instruction.substring(2, 4), 16);
                registers.setDataRegisterValue(register, (byte) (registers.getDataRegisterValue(register) + value));
                break;

            case ('8'):

                register1 = Integer.parseInt(instruction.substring(1, 2), 16);
                register2 = Integer.parseInt(instruction.substring(2, 3), 16);

                int registerValue1 = registers.getDataRegisterValue(register1) & 0xff;
                int registerValue2 = registers.getDataRegisterValue(register2) & 0xff;

                switch (instruction.substring(3, 4)){
                    case ("0"):
                        registers.setDataRegisterValue(register1, (byte) registerValue2);
                        break;

                    case("1"):
                        registers.setDataRegisterValue(register1, (byte) (registerValue1 | registerValue2));
                        break;

                    case("2"):
                        registers.setDataRegisterValue(register1, (byte) (registerValue1 & registerValue2));
                        break;

                    case("3"):
                        registers.setDataRegisterValue(register1, (byte) (registerValue1 ^ registerValue2));
                        break;

                    case("4"):
                        value = registerValue1 + registerValue2;
                        if (value > 255)
                            registers.setDataRegisterValue(0xf, (byte) 1);
                        registers.setDataRegisterValue(register1, (byte) (registerValue1 + registerValue2));
                        break;

                    case("5"):
                        value = registerValue1 - registerValue2;
                        if (value < 0)
                            registers.setDataRegisterValue(0xf, (byte) 1);
                        registers.setDataRegisterValue(register1, (byte) value);
                        break;

                    case("6"):
                        registers.setDataRegisterValue(register1, (byte) (registerValue1 >> 1));
                        registers.setDataRegisterValue(0xf, (byte) (registerValue1 & 1));
                        break;

                    case("7"):
                        value = registerValue2 - registerValue1;
                        if (value < 0)
                            registers.setDataRegisterValue(0xf, (byte) 1);
                        registers.setDataRegisterValue(register1, (byte) value);
                        break;

                    case("E"):
                        registers.setDataRegisterValue(register1, (byte) (registerValue1 << 1));
                        registers.setDataRegisterValue(0xf, (byte) (registerValue1 & 0x80));
                        break;
                    default:
                        System.out.println("Unhandled arithmetic");
                        break;
                }
                break;

            case ('9'):
                register1 = Integer.parseInt(instruction.substring(1, 2), 16);
                register2 = Integer.parseInt(instruction.substring(2, 3), 16);
                if (registers.getDataRegisterValue(register1) != registers.getDataRegisterValue(register2))
                    instructionAddress += 2;
                break;

            case ('a'):
                registers.setAddressRegister(Integer.parseInt(instruction.substring(1, 4), 16));
                break;

            case ('b'):
                instructionAddress = Integer.parseInt(instruction.substring(1), 16) + registers.getDataRegisterValue(0);
                break;

            case ('c'):
                Random random = new Random();
                int randomNumber = random.nextInt(256) & Integer.parseInt(instruction.substring(2, 4), 16);
                registers.setDataRegisterValue(Integer.parseInt(instruction.substring(1, 2),16), (byte) randomNumber);
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
