import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * An excessively-featured (overcomplicated?) FizzBuzz implementation
 * 
 * @author <em>Zach Baldwin</em>
 * @version 2020.04.25
 */
public class FizzBuzz {

    /** Stores the default start number */
    private static final int defaultStartNumber = 0;

    /** Stores the default end number */
    private static final int defaultEndNumber = 100;

    /** Stores the default multiple value for the number corresponding to "Fizz" */
    private static final int defaultFizzMultiple = 3;

    /** Stores the default multiple value for the number corresponding to "Buzz" */
    private static final int defaultBuzzMultiple = 5;

    /** Stores the default delimiter for the FizzBuzz generation */
    private static final String defaultDelimiter = System.lineSeparator();

    /** Stores the default string to represent "Fizz" */
    private static final String defaultFizzString = "Fizz";

    /** Stores the default string to represent "Buzz" */
    private static final String defaultBuzzString = "Buzz";

    /**
     * Returns a system-newline separated list of numbers converted to FizzBuzz notation
     *
     * @param fizzMultiple The multiple number that defines a "Fizz"
     * @param buzzMultiple The multiple number that defines a "Buzz"
     * @param startNumber  The start number for FizzBuzz natation generation (must be positive)
     * @param endNumber    The ending number for FizzBuzz generation (inclusive)
     * @param delimiter    The delimiter to separate the numbers in the output
     * @param fizzString   The string to represent a "Fizz"
     * @param buzzString   The string to represent a "Buzz"
     * @return             The string containing the numbers from 0 to n converted to FizzBuzz notation
     */
    private static String FizzBuzzGenerator(int fizzMultiple, int buzzMultiple,
                                            int startNumber, int endNumber,
                                            String delimiter, String fizzString, String buzzString) {
        
        // Make our string builder so that we don't have to keep resizing the output string
        StringBuilder outputBuilder = new StringBuilder();

        // Go through all of the numbers fom 0 to n
        for (int i = startNumber; i <= endNumber; i++) {
           
            // If the number is not a multiple of "Fizz" or "Buzz" then just output it
            if ((i % fizzMultiple != 0 && i % buzzMultiple != 0) || i == 0) {
                outputBuilder.append(i);
            } else {
                // If the number is a multiple of "Fizz" then add it to the output
                if (i % fizzMultiple == 0) {
                    outputBuilder.append(fizzString);
                }
            
                // If the number is a multiple of "Buzz" add it
                // This means that on numbers that are multiples of both "Fizz" and "Buzz"
                // then both "Fizz" and "Buzz" will be added 
                if (i % buzzMultiple == 0) {
                    outputBuilder.append(buzzString);
                }
            }
           
            // Done with this number, add the newline unless this is the last number
            if (i != endNumber) {
                outputBuilder.append(delimiter);
            }
        }
      
        // Return the newly generated list that contains the "Fizz" and "Buzz"
        return outputBuilder.toString();
    }

    /**
     * Prints the help message and exits with no error (exit code 0)
     */
    private static void printHelp() {
        System.out.println(
                        "FizzBuzz generator - Zach Baldwin April 2020\n\n" +
                        "USAGE:\n" +
                        "    $ java FizzBuzz [OPTIONS]\n\n" +
                        "Where options are as follows:\n" +
                        "    -f, --fizz      <fizzNumber>       The multiple for \"Fizz\" numbers (default: " + defaultFizzMultiple+ ")\n" +
                        "    -b, --buzz      <buzzNumber>       The multiple for \"Buzz\" numbers (default: " + defaultBuzzMultiple + ")\n" +
                        "    --fizzstring    <fizzString>       The string that represents \"Fizz\"\n" +
                        "    --buzzstring    <buzzString>       The string that represents \"Buzz\"\n" +
                        "    -s, --start     <startingNumber>   The number to start calculating FizzBuzz from (default: " + defaultStartNumber + ")\n" +
                        "    -e, --end       <endingNumber>     The number to stop calculating FizzBuzz on (inclusive, default: " + defaultEndNumber + ")\n" +
                        "    -d, --delimiter <delimiter>        The delimiter to separate the output values (default: system-newline)\n" +
                        "    -o, --output    <filename>         Output the FizzBuzz numbering scheme to a <filename> (default prints to standard output)\n" +
                        "    --headlessprint <y|n>              Do not ask to print to screen if the output file is unable to be opened\n" +
                        "                                       y - Yes, output to standard output if there is a problem\n" +
                        "                                       n - No, do not output to standard output, just abort\n" +
                        "                                       No argument defaults to 'n'\n" +
                        "    -h, -?, --help                     Output this help menu to standard output.\n\n" +
                        "Exit codes:\n" +
                        "    0 - Success\n" +
                        "    1 - Number format error\n" +
                        "    2 - Invalid number (i.e. the start number is greater than the end number)\n" +
                        "    3 - Missing argument\n" +
                        "    4 - Invalid argument encountered\n" +
                        "    5 - File error encountered while in headless mode and standard output was not specified\n\n");
        System.exit(0);
    }

    /**
     * Runs the FizzBuzz generator
     *
     * @param args See the printHelp() method
     */
    public static void main(String[] args) {
 
        if (args.length == 0) {
            System.out.println(FizzBuzzGenerator(defaultFizzMultiple, defaultBuzzMultiple, defaultStartNumber,
                    defaultEndNumber, defaultDelimiter, defaultFizzString, defaultBuzzString));
        } else if (args[0].equals("-h") || args[0].equals("-?") || args[0].equals("--help")) {
            printHelp();
        } else {

            // Setup arguments for the FizzBuzz generation
            int fizzMultiple  = defaultFizzMultiple;
            int buzzMultiple  = defaultBuzzMultiple;
            int startNumber   = defaultStartNumber;
            int endNumber     = defaultEndNumber;
            String delimiter  = defaultDelimiter;
            String fizzString = defaultFizzString;
            String buzzString = defaultBuzzString;
            String outputFilename   = null;
            boolean promptForOutput = true;
            boolean headlessPrint   = false;

            // Scan for custom arguments
            int i = 0;
            try {
                while (i < args.length) {

                    switch (args[i].toLowerCase()) {
                        case "-f":
                        case "--fizz":
                            fizzMultiple = Integer.parseInt(args[i + 1]);
                            i++;
                            break;
                        case "-b":
                        case "--buzz":
                            buzzMultiple = Integer.parseInt(args[i = 1]);
                            i++;
                            break;
                        case "--fizzstring":
                            fizzString = args[i + 1];
                            i++;
                            break;
                        case "--buzzstring":
                            buzzString = args[i + 1];
                            i++;
                            break;
                        case "-s":
                        case "--start":
                            startNumber = Integer.parseInt(args[i + 1]);
                            i++;
                            break;
                        case "-e":
                        case "--end":
                            endNumber = Integer.parseInt(args[i + 1]);
                            i++;
                            break;
                        case "-d":
                        case "--delimiter":
                            delimiter = args[i + 1];
                            i++;
                            break;
                        case "-o":
                        case "--output":
                            outputFilename = args[i + 1];
                            i++;
                            break;
                        case "--headlessprint":
                            promptForOutput = false;
                            if (args[i + 1].toLowerCase().equals("y")) {
                                headlessPrint = true;
                            } else if (args[i + 1].toLowerCase().equals("n")) {
                                headlessPrint = false;
                            } else {
                                throw new InvalidParameterException();
                            }
                            i++;
                            break;
                        case "-?":
                        case "-h":
                        case "--help":
                            printHelp();
                        default:
                            throw new InvalidParameterException();
                    }

                    // Next argument
                    i++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number provided: " + args[i]);
                System.exit(1);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: expected argument but none found: " + args[i]);
                System.exit(3);
            } catch (InvalidParameterException e) {
                System.out.println("Error: invalid argument encountered: " + args[i]);
                System.exit(4);
            }

            // Make sure that the start number is less than the end number
            if (startNumber > endNumber) {
                System.out.println("Error: the start number cannot be greater than the end number");
                System.exit(2);
            }

            // Done parsing arguments, now run the FizzBuzz generator and output the result
            String fizzBuzzOutput = FizzBuzzGenerator(fizzMultiple, buzzMultiple, startNumber, endNumber,
                    delimiter, fizzString, buzzString);

            if (outputFilename != null) {

                // Open the file for writing
                try {
                    BufferedWriter outputBuffer = new BufferedWriter(new FileWriter(outputFilename));
                    outputBuffer.write(fizzBuzzOutput);
                    outputBuffer.close();
                } catch (IOException e) {
                    if (promptForOutput) {
                        // Prompt the user for whether or not they want their FizzBuzz list to be output to the terminal
                        Scanner inputScanner = new Scanner(System.in);
                        System.out.print("There was a problem opening the output file, do you want to print to the terminal? [y|N]: ");
                        if (inputScanner.next().equals("y")) {
                            System.out.println(fizzBuzzOutput);
                        }
                    } else {
                        if (headlessPrint) {
                            System.out.println(fizzBuzzOutput);
                        } else {
                            System.err.println("File error: aborting");
                            System.exit(5);
                        }
                    }
                }
            } else {
                System.out.println(fizzBuzzOutput);
            }
        }
    }
}
