package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class CustomFileReader extends CustomIO {

    public CountryVATList ReadFile(String filePath) {

        CountryVATList countryVATList = new CountryVATList();
        try (Scanner scanner = new Scanner(new BufferedReader(new java.io.FileReader(filePath)))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                CountryVAT countryVAT = parseLine(line);
                try {
                    countryVATList.AddNewCountryVAT(countryVAT);
                }
                catch (CustomException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countryVATList;
    }

    private CountryVAT parseLine(String line) {

        CountryVAT result = null;
        String[] countryVATInfo = line.split(FILE_READ_ITEM_DELIMITER);
        String code = countryVATInfo[0];
        String name = countryVATInfo[1];
        Double maxVAT = Double.valueOf(0);
        Double minVAT = Double.valueOf(0);
        Boolean hasDPH = false;
        try {
            maxVAT = CustomParser.parsingStringToDouble(countryVATInfo[2]);
            minVAT = CustomParser.parsingStringToDouble(countryVATInfo[3]);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        if (countryVATInfo[4].equals("false")) {
            hasDPH = false;
        } else if (countryVATInfo[4].equals("true")) {
            hasDPH = true;
        }
        try {
            result = new CountryVAT(name, code, maxVAT, minVAT, hasDPH);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
