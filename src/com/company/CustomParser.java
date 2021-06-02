package com.company;

import java.text.ParseException;

public class CustomParser extends CustomIO{

    public static Double parsingStringToDouble(String value)
    {
        Double tmp  = Double.valueOf(0);
        try {
            tmp = Double.valueOf(value);
        }
        catch (NumberFormatException e) {
            try {
                Number tmp2 =  decimalFormat().parse(value);
                if(tmp2 instanceof Double)
                    tmp = tmp2.doubleValue();
                else
                    throw new CustomException("Error: Could not parse "+value+" to Double. Correct the format");
            } catch (ParseException | CustomException parseException) {
                System.out.println(parseException.getMessage());
                tmp = Double.valueOf(-1);
            }
        }
        return tmp;
    }

    public static Integer parsingStringToInteger(String value)
    {
        Integer tmp  = Integer.valueOf(0);
        try {
            tmp = Integer.valueOf(value);
        }
        catch (NumberFormatException e) {
            try {
                Number tmp2 =  decimalFormat().parse(value);
                if(tmp2 instanceof Integer)
                    tmp = tmp2.intValue();
                else
                    throw new CustomException("Error: Could not parse "+value+" to Integer. Correct the format");
            } catch (ParseException | CustomException parseException) {
                System.out.println(parseException.getMessage());
                tmp = Integer.valueOf(-1);
            }
        }
        return tmp;
    }
}
