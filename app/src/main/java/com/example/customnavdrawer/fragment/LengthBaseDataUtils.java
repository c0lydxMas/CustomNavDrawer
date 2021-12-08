package com.example.customnavdrawer.fragment;

public class LengthBaseDataUtils {

    public static LengthBase[] getLengthBase(){
        LengthBase centimeter = new LengthBase("Centimeter");
        LengthBase meter = new LengthBase("Meter");
        LengthBase inch = new LengthBase("Inch");

        return new LengthBase[] {centimeter,meter,inch};
    }

}
