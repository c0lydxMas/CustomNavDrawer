package com.example.customnavdrawer.fragment;

public class LengthBase {

    private String lengthBase;

    public LengthBase(String lengthBase){
        this.lengthBase = lengthBase;
    }

    public String getLengthBase() {
        return lengthBase;
    }

    public void setLengthBase(String lengthBase) {
        this.lengthBase = lengthBase;
    }

    // Text show in Spinner
    @Override
    public String toString()  {
        return this.getLengthBase();
    }

}
