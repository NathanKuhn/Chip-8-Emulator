package com.githhub.nathankuhn.chip8;

public class ROM {

    public byte[] content;
    public final int length;

    public ROM(int[] content) {
        length = content.length;
        this.content = new byte[length];
        for (int i = 0; i < length; i++) {
            this.content[i] = (byte) content[i];
        }
    }

}
