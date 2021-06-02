package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CountryVATList {
    private ArrayList<CountryVAT> countryVATS = new ArrayList<>();

    public CountryVATList() {
    }

    public CountryVATList(ArrayList<CountryVAT> countryVATS) throws CustomException {
        if(countryVATS.size() > 0)
            this.countryVATS.addAll(countryVATS);
        else throw new CustomException("Error: The country VAT list cannot be empty");
    }

    public void Clear()
    {
        this.countryVATS.clear();
    }

    public void AddNewCountryVAT(CountryVAT countryVAT) throws CustomException {
        if(countryVAT != null)
            this.countryVATS.add(countryVAT);
        else throw new CustomException("Error: Country VAT object is null.");
    }

    public void Replace(ArrayList<CountryVAT> countryVATS) throws CustomException {
        Clear();
        this.countryVATS.addAll(countryVATS);
    }

    public void AddNewCountryVATCollection(ArrayList<CountryVAT> countryVATS) throws CustomException {
        if(countryVATS.size() > 0)
            this.countryVATS.addAll(countryVATS);
        else throw new CustomException("Error: The country VAT list cannot be empty");
    }

    public ArrayList<CountryVAT> GetCountryVATCollection()  {
        return this.countryVATS;
    }
    public Integer Size()  {
        return this.countryVATS.size();
    }
    public ArrayList<String> GetCountryVATCollectionInfo(String data)  {
        ArrayList<String> result = new ArrayList<>();
        for (CountryVAT vat:
                GetCountryVATCollection()) {
            if(data.equals("BasicInfo"))
                result.add(vat.GetBasicInfo());
            else if(data.equals("Name"))
                result.add(vat.GetName());
        }
        return result;
    }
    public CountryVATList GetCountryVATUsersDPH(CountryVATList countryVATlist,int use) throws CustomException {
        CountryVATList result = new CountryVATList();
        for (CountryVAT vat:
                countryVATlist.GetCountryVATCollection()) {
            if(use == 1 && vat.HasDPH())
                result.AddNewCountryVAT(vat);
            else if(use == 0 && !vat.HasDPH())
                result.AddNewCountryVAT(vat);
        }
        return result;
    }

    public CountryVATList GetCountryVATGreaterThen(CountryVATList countryVATlist,Double VAT) throws CustomException {
        CountryVATList result = new CountryVATList();
        for (CountryVAT vat:
                countryVATlist.GetCountryVATCollection()) {
            if(vat.GetPrimaryVAT() > VAT)
                result.AddNewCountryVAT(vat);
        }
        return result;
    }

    public CountryVATList Sort(CountryVATList countryVATlist) throws CustomException {
        ArrayList<CountryVAT> tmpListVAT = new ArrayList<>();
        tmpListVAT.addAll(countryVATlist.GetCountryVATCollection());
        Collections.sort(tmpListVAT, new CustomComparator());
        countryVATlist.Replace(tmpListVAT);
        return countryVATlist;
    }

    public CountryVATList GetUnusedCountryVATS(CountryVATList countryVATlist) throws CustomException
    {
        CountryVATList result = new CountryVATList();
        for (CountryVAT vat:
                GetCountryVATCollection()) {
            if(!countryVATlist.GetCountryVATCollection().contains(vat)) {
                result.AddNewCountryVAT(vat);
            }
        }
        return result;
    }

}
