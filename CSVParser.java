import java.util.ArrayList;

/**
 * A simple CSV parsing utility
 * (WARNING: Treat this as "AS-IS" since it may not work on all CSV file formats)
 *
 * @author <em>Zach Baldwin</em>
 * @version 2020.05.17
 */
public class CSVParser {

    /** The delimiter that we are using for parsing the columns */
    private static final char parsingDelimiter = ',';

    /**
     * Parse a line and return an array of the rows using the default delimiter of ','
     *
     * @param line The input line to be parsed
     * @return Returns the input line as an array of Strings corresponding to the columns in the input line
     */
    public static String[] parseLine(String line) {
        return parseLineDelimited(line, parsingDelimiter);
    }

    /**
     * Parse a line and return an array of the columns using a custom delimiter<br>
     * <b>Note:</b> this method cannot use "s (quotes) as the delimiter and it does not preform a check
     *     for this invalid delimiter
     *
     * @param line The string that makes up the input line (row) to be parsed
     * @param parsingDelimiter The delimiter to be used to separate columns
     * @return Returns an array of strings corresponding to the columns in the input line
     */
    public static String[] parseLineDelimited(String line, char parsingDelimiter) {

        // Indicates that an odd number of quotes have been found
        // (True = odd, false = even) - Think C-style true-false with a % operation
        boolean quoteParity = false;

        // Indicates that the previous character was a quote
        boolean previousWasQuote = false;

        // Setup the output array
        ArrayList<String> outputArray = new ArrayList<>(); // Initial capacity of 10

        // Setup the intermediate string string builder
        StringBuilder columnText = new StringBuilder();

        // Go over the input line character by character
        // Each iteration should get the next character, determine if it is the start of the
        // next column and either add the current string to the output array or append the
        // current character to the current column's text
        for (int i = 0; i < line.length(); i++) {

            // Get the current character
            char currentCharacter = line.charAt(i);

            // Is this a quote?
            if (currentCharacter == '\"') {

                // Swap the quote parity
                quoteParity = !quoteParity;

                // Two quotes in a row means a single quote
                if (previousWasQuote) {
                    columnText.append('\"');
                    previousWasQuote = false;
                } else {
                    previousWasQuote = true;
                }
            } else { // Even number of quotes have been parsed in the current column

                // Check to see if the character is the delimiter
                if (!quoteParity && currentCharacter == parsingDelimiter) {

                    // Reset for the next column and add the current column to the output array
                    outputArray.add(columnText.toString());
                    columnText = new StringBuilder();
                } else {

                    // Not the delimiter, add it to the current column's text
                    columnText.append(currentCharacter);
                }

                // Reset the previous quote flag (because this, which will be the previous, is not a quote)
                previousWasQuote = false;
            }
        }

        outputArray.add(columnText.toString());
//        // In the event that the end of the line ended in a quote or  append the last column
//        if (previousWasQuote)
//            outputArray.add(columnText.toString());
//
//        // In the event that the last column is empty, add an empty column
//        if (line.charAt(line.length() - 1) == parsingDelimiter)
//            outputArray.add("");

        // Return the array of columns
        return outputArray.toArray(new String[outputArray.size()]); // Can't cast this
    }
}
