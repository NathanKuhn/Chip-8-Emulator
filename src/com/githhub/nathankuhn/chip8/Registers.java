package com.githhub.nathankuhn.chip8;

public class Registers {

    private static final int DATA_REGISTERS = 16;

    private byte[] dataRegisters;
    private int addressRegister;

    public Registers() {
        dataRegisters = new byte[DATA_REGISTERS];
        addressRegister = 0;
    }

    public void print() {
        for (int i = 0; i < dataRegisters.length; i++) {
            System.out.print("+---" + (i == dataRegisters.length - 1 ? "+\n" : ""));
        }
        System.out.print("| ");
        for (int i = 0; i < dataRegisters.length; i++) {
            System.out.print(Integer.toHexString(i) + " |" + (i == dataRegisters.length - 1 ? "\n" : " "));
        }
        for (int i = 0; i < dataRegisters.length; i++) {
            System.out.print("+---" + (i == dataRegisters.length - 1 ? "+\n" : ""));
        }
        System.out.print("| ");
        for (int i = 0; i < dataRegisters.length; i++) {
            System.out.print(Integer.toHexString(dataRegisters[i]) + " |" + (i == dataRegisters.length - 1 ? "\n" : " "));
        }
        for (int i = 0; i < dataRegisters.length; i++) {
            System.out.print("+---" + (i == dataRegisters.length - 1 ? "+\n" : ""));
        }
    }

    public byte getDataRegisterValue(int register) {
        return dataRegisters[register];
    }
    public int getAddressRegister() {
        return addressRegister;
    }

    public void setDataRegisterValue(int register, byte value) {
        dataRegisters[register] = value;
    }
    public void setAddressRegister(int value) {
        assert (value < Memory.MEMORY_SIZE) : "Cannot set address register to a non-existent address";
        addressRegister = value;
    }

}
