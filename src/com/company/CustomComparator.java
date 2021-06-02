package com.company;

import java.util.Comparator;

public class CustomComparator implements Comparator<CountryVAT> {
    @Override
    public int compare(CountryVAT first, CountryVAT second) {
        return second.GetPrimaryVAT().compareTo(first.GetPrimaryVAT());
    }

    @Override
    public Comparator<CountryVAT> reversed() {
        return Comparator.super.reversed();
    }
}
