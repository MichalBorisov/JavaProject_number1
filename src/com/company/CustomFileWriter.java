package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomFileWriter {

    public static void WriteToFile(CountryVATList toPrint, CountryVATList unusedCountryVAT, Double Vat, String filePath)
    {
        filePath = filePath.replace("***", Vat.toString());
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (String info :
                    toPrint.GetCountryVATCollectionInfo("BasicInfo")) {
                writer.println(info);
            }
            writer.println("=================");
            writer.print("VAT is less or equal to " + Vat.toString() + " in these countries: ");
            for (String info :
                    unusedCountryVAT.GetCountryVATCollectionInfo("Name")) {
                writer.print(info + " | ");
            }
        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}
