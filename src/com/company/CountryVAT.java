package com.company;

import java.util.Comparator;

public class CountryVAT {
    private String name;
    private String code;
    private Double primaryVAT;
    private Double minVAT;
    private Boolean hasDPH;

    public CountryVAT()
    {}

    public CountryVAT(String name, String code, Double primaryVAT, Double minVAT, Boolean hasDPH) throws CustomException {
        this.name = name;
        if(name.equals("") || name.equals(null) || name.equals(" ")) throw new CustomException("Error: Country name cannot be empty");

        this.code = code;
        if(code.equals("") || code.equals(null) || code.equals(" ")) throw new CustomException("Error: Country code cannot be empty");

        this.primaryVAT = primaryVAT;
        if(primaryVAT <= 0) throw new CustomException("Error: Max VAT cannot be less or equal 0");

        this.minVAT = minVAT;
        if(minVAT <= 0) throw new CustomException("Error: Min VAT cannot be less or equal 0");

        this.hasDPH = hasDPH;
    }

    public String GetName()
    {
        return this.name;
    }
    public String GetCode()
    {
        return this.code;
    }
    public Double GetPrimaryVAT()
    {
        return this.primaryVAT;
    }
    public Double GetMinVAT()
    {
        return this.minVAT;
    }
    public Boolean HasDPH()
    {
        return this.hasDPH;
    }
    public String GetBasicInfo()
    {
            return  GetName() + " (" +  GetCode() +"): " + GetPrimaryVAT() +"%";
    }


}
