package com.company;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CustomIO {

        public static final String FILE_READ_ITEM_DELIMITER = "\t";
        public static final String NEW_LINE = "\r\n";

        private static String decimalSeparator = "-1";
        private static String groupSeparator = "-1";

        private static DecimalFormat  decimalFormat = new DecimalFormat();
        private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        public static void setDecimalSeparator(char charr) {
                symbols.setDecimalSeparator(charr);
                decimalFormat.setDecimalFormatSymbols(symbols);
        }

        public static void setGroupSeparator(char charr) {
                symbols.setGroupingSeparator(charr);
                decimalFormat.setDecimalFormatSymbols(symbols);
        }
        public static DecimalFormat decimalFormat()
        {
              return decimalFormat;
        }
}
