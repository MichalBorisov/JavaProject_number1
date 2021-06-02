package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static final String INPUT_FILE = "vat-eu.csv";
    private static final String OUTPUT_FILE = "vat-eu-***.csv";

    public static void main(String[] args) {

        CustomFileReader customFileReader = new CustomFileReader();
        CountryVATList countryVATList = new CountryVATList();

        String inputText = "-1";
        Integer command = -1;
        Integer standardCommand = -1;
        Boolean firstRun = true;
        Boolean isStandardCommand = false;
        do {
            Scanner scanner = new Scanner(System.in);
            {
                if (firstRun) {
                    System.out.println("First run");
                    boolean result = false;
                    do{
                        result = updateNumberFormat(scanner);
                    }while(result == false);
                    printCommands();
                    firstRun = false;
                }
                System.out.print("enter command :");
                inputText = scanner.nextLine();
                if(inputText.equals(""))
                {
                    command = 5;
                    isStandardCommand = true;
                }
            }
            if (!inputText.equals("Exit")) {
                if(!command.equals(5))
                    command = CustomParser.parsingStringToInteger(inputText);

                if (command == 1) {
                    if(countryVATList.Size() == 0)
                    {
                        countryVATList = customFileReader.ReadFile(INPUT_FILE);
                        if (countryVATList.Size() > 0)
                            System.out.println("File is successfully loaded. Countries loaded: " + countryVATList.Size());
                    }else {
                        System.out.println("Clear List before loading new file");
                    }
                }
                if (command == 2) {
                    if(countryVATList.Size() == 0) {
                        System.out.print("enter full path to file :");
                        String customFile = scanner.nextLine();
                        countryVATList = customFileReader.ReadFile(customFile);
                        if (countryVATList.Size() > 0)
                            System.out.println("File is successfully loaded. Countries loaded: " + countryVATList.Size());
                    } else {
                        System.out.println("Clear List before loading new file");
                    }
                } else if (command == 3) {
                    printVATInfo(countryVATList);
                } else if (command == 4 || command == 5) {
                    printVATInfo(countryVATList, scanner, command, isStandardCommand);
                } else if (command == 8) {
                    System.out.println("Are you sure you want to clear list of countries VAT? Press 1 to clear");
                    String clear = scanner.nextLine();
                    if(CustomParser.parsingStringToInteger(clear).equals(1)) {
                        countryVATList.Clear();
                        System.out.println("List of countries VAT is cleared");
                    }
                } else if (command == 9) {
                    boolean result = false;
                    do{
                        result = updateNumberFormat(scanner);
                    }while(result == false);
                }
                command = 0;
                isStandardCommand = false;
            }
            if (inputText.equals("Help") || inputText.equals("help")) {
                printCommands();
            }
        } while (!inputText.equals("Exit") && !inputText.equals("exit"));
    }

    private static void printVATInfo(CountryVATList countryVATList)
    {
        if(countryVATList.Size() == 0) {
            System.out.println("Load the file first");
            return;
        }
        for (String info:
                countryVATList.GetCountryVATCollectionInfo("BasicInfo")) {
            System.out.println(info);
        }
    }
    private static void printVATInfo(CountryVATList countryVATList,Scanner scanner, Integer command, Boolean isStandardCommand) {
        Integer useDPH = -1;
        Double vat = Double.valueOf(-1);
        if (!isStandardCommand) {
            System.out.println("uses DPH: 1(yes), 0(no)");
            useDPH = CustomParser.parsingStringToInteger(scanner.nextLine());
            if(useDPH.equals(-1)) {
                System.out.println("Error with reading input");
                return;
            }
            System.out.print("Primary Vat = ");
            vat = CustomParser.parsingStringToDouble(scanner.nextLine());
            if(vat.equals(-1)) {
                System.out.println("VAT cannot be less then 0");
                return;
            }
        } else if (isStandardCommand) {
            useDPH = 0;
            vat = Double.valueOf(20);
        }
        try {
            printVATInfoGreaterThen(countryVATList, vat, useDPH, command);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printVATInfoGreaterThen(CountryVATList countryVATList, Double vat, Integer useDPH, Integer command) throws CustomException {
        System.out.println();
        System.out.println();
        if (countryVATList.Size() == 0) {
            System.out.println("Load the file first");
            return;
        }
        CountryVATList toPrint = countryVATList.GetCountryVATGreaterThen(countryVATList, vat);
        toPrint = countryVATList.GetCountryVATUsersDPH(toPrint, useDPH);
        toPrint = toPrint.Sort(toPrint);
        CountryVATList unusedCountryVAT = countryVATList.GetUnusedCountryVATS(toPrint);
        if(command == 5) {
            printToScreen(toPrint, unusedCountryVAT, vat);
        }
        if(command == 4) {
            printToScreen(toPrint, unusedCountryVAT,vat);
            printToFile(toPrint, unusedCountryVAT, vat);
        }
        System.out.println();
        System.out.println();
    }

    private static void printToScreen(CountryVATList toPrint, CountryVATList unusedCountryVAT, Double vat)
    {
        for (String info :
                toPrint.GetCountryVATCollectionInfo("BasicInfo")) {
            System.out.println(info);
        }
        System.out.println("=================");
        System.out.print("VAT is less or equal to " + vat.toString() + " in these countries: ");
        for (String info :
                unusedCountryVAT.GetCountryVATCollectionInfo("Name")) {
            System.out.print(info + " | ");
        }
    }
    private static void printToFile(CountryVATList toPrint, CountryVATList unusedCountryVAT, Double vat)
    {
        CustomFileWriter.WriteToFile(toPrint,unusedCountryVAT,vat,OUTPUT_FILE);
    }
    private static Boolean updateNumberFormat(Scanner scanner)
    {
        try {
            System.out.println("Please, Set Number Group Separator");
            CustomIO.setGroupSeparator(scanner.nextLine().charAt(0));
            System.out.println("Please, Set Decimal Group Separator");
            CustomIO.setDecimalSeparator(scanner.nextLine().charAt(0));
        }
        catch (Exception e)
        {
            System.out.println("Improper sign, please use another");
            return false;
        }
        return true;
    }
    private static void printCommands()
    {
        System.out.println("To exit, type exit");
        System.out.println("Commands are to be called by the numbers");
        System.out.println("1 - Load standard file");
        System.out.println("2 - Load custom file");
        System.out.println("3 - Print basic info");
        System.out.println("4 - Print to file country VAT bigger then... (uses DPH yes/no)");
        System.out.println("5 - Print to screen country VAT bigger then... (uses DPH yes/no)");
        System.out.println("8 - Clear loaded list");
        System.out.println("9 - Update Number format");
    }
}
