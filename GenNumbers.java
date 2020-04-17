import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Generates a random list of numbers in an output file that
 * are system-newline separated.
 *
 * @author <em>Zach Baldwin</em>
 * @version 2020.04.16
 */
public class GenNumbers {

    /**
     * Generate the output file
     *
     * @param args The number of numbers to generate and the file to put them in
     */
    public static void main(String[] args) {

        // Make sure that there are the correct number of arguments
        if (args.length != 2 || args[0].equals("?") || args[0].toLowerCase().equals("h")) {
            System.out.println(
                    "Random Number File Filler Utility - Zach Baldwin April 2020\n" +
                    "USAGE:\n" +
                    "    $ java GenNumbers number filename\n\n" +
                    "    number   - The quantity of random numbers to generate\n" +
                    "    filename - The file to which the numbers will be stored\n");
            System.exit(1);
        }

        // CLI args
        String numberQuantity = args[0];
        String outputFilename = args[1];

        // Open the file and send a bunch of random numbers to it
        try {
            // Get the number of numbers to generate
            int numberOfNumbers = Integer.parseInt(numberQuantity);

            // Open the output file
            BufferedWriter bufOut = new BufferedWriter(new FileWriter(outputFilename));

            // "Random" number generator
            Random numberGen = new Random();

            for (int i = 0; i < numberOfNumbers; i++) {
                bufOut.write(Integer.toString(numberGen.nextInt()));
                bufOut.newLine();
            }

            // Close the output file
            bufOut.close();
        } catch (IOException e) {
            System.out.println("A problem was encountered while accessing the output file.");
        } catch (NumberFormatException e) {
            System.out.println("The value entered: \"" + numberQuantity + "\" is not a valid integer.");
        }
    }
}
