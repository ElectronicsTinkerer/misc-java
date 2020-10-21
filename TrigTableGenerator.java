/**
 * Generates a lookup table for sin, cos, and tan with a given number of steps
 *
 * @author <em>Zach Baldwin</em>
 * @version 2020.10.03
 */

public class TrigTableGenerator {

    /** Types of functions that can be preformed */
    private enum Functions {
        SIN, COS, TAN, X2, X3
    }

    /** Types of angle units */
    private enum AngleTypes {
        DEG, RAD
    }

    /** Default start value for table generation */
    private static int defaultStartValue = 0;

    /** Default number of columns for output table */
    private static int defaultColumnQuantity = 4;

    /**
     * Computes the result of the specified expression and returns it as a string
     *
     * @param x The value to be passed to the specified function
     * @param function The function to have x as the input of
     * @param angleType The type of angle (deg or rad) to use for the computation
     * @return The result of the computed expression in string format (as a double)
     */
    private static String computeExpression(double x, Functions function, AngleTypes angleType) {

        double result;
        switch (function) {
            case COS:
                if (AngleTypes.DEG == angleType) {
                    result = Math.cos((x * Math.PI) / 180.0);
                    return Double.toString(result);
                }
                if (AngleTypes.RAD == angleType) {
                    result = Math.cos(x);
                    return Double.toString(result);
                }
                break;
            case SIN:
                if (AngleTypes.DEG == angleType) {
                    result = Math.sin((x * Math.PI) / 180.0);
                    return Double.toString(result);
                }
                if (AngleTypes.RAD == angleType) {
                     result = Math.sin(x);
                    return Double.toString(result);
                }
                break;
            case TAN:
                if (AngleTypes.DEG == angleType) {
                    result = Math.tan((x * Math.PI) / 180.0);
                    return Double.toString(result);
                }
                if (AngleTypes.RAD == angleType) {
                    result = Math.tan(x);
                    return Double.toString(result);
                }
                break;
            case X2:
                result = Math.pow(x, 2);
                return Double.toString(result);
            case X3:
                result = Math.pow(x, 3);
                return Double.toString(result);
        }
        return null;
    }

    /**
     * Prints the help menu (but does not stop execution)
     */
    private static void printHelp() {
        System.out.println(
                "Table generator - Zach Baldwin October 2020\n" +
                "This software is free to use but is provided 'AS-IS' \n" +
                "and not guaranteed for any particular purpose.\n" +
                "USAGE:\n" +
                "$ java TrigTableGenerator [options]\n\n" +
                "Required Options:\n" +
                "    -fn function ................ Specify the function table to generate\n" +
                "                                  Options: sin, cos, tan, x2, x3\n" +
                "    -s, --steps steps ........... Number of steps to take when generating table\n" +
                "    -f, --final value ........... Final (stop) value\n" +
                "Optional Options:\n" +
                "    -a, --angle angletype ....... Angle type: deg or rad (default deg)\n" +
                "    -i, --initial value ......... Initial (start) value - default: 0\n" +
                "    -c, -cols cols .............. Number of columns to generate in output array\n" +
                "    -?, -h, --help .............. Help: Display this menu\n" +
                "    --float ..................... Append an 'f' to the numbers in table\n"
        );
    }

    public static void main(String[] args) {

        // Arguments
        Functions function = null;
        AngleTypes angleType = AngleTypes.DEG;  // Default
        int totalSteps = 0;
        double startValue = defaultStartValue;  // Default
        double stopValue = 0;
        int numberOfColumns = defaultColumnQuantity;
        String appendF = "";

        try {
            int i = 0;
            while (i < args.length) {
                switch (args[i]) {
                    case "-fn":
                        switch (args[++i]) {
                            case "sin":
                                function = Functions.SIN;
                                break;
                            case "cos":
                                function = Functions.COS;
                                break;
                            case "tan":
                                function = Functions.TAN;
                                break;
                            case "x2":
                                function = Functions.X2;
                                break;
                            case "x3":
                                function = Functions.X3;
                                break;
                            default:
                                System.out.println("Error: invalid function, aborting." + args[i]);
                                printHelp();
                                System.exit(-1);
                        }
                        break;

                    case "--steps":
                    case "-s":
                        try {
                            totalSteps = Integer.parseUnsignedInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Non-integer where integer expected, aborting. " + args[i]);
                            System.exit(-1);
                        }
                        break;

                    case "--stop":
                    case "--final":
                    case "-f":
                        try {
                            stopValue = Double.parseDouble(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Non-double where double expected, aborting. " + args[i]);
                            System.exit(-1);
                        }
                        break;

                    case "--angle":
                    case "-a":
                        switch (args[++i]) {
                            case "degrees":
                            case "degree":
                            case "deg":
                                angleType = AngleTypes.DEG;
                                break;
                            case "radians":
                            case "radian":
                            case "rad":
                                angleType = AngleTypes.RAD;
                                break;
                            default:
                                System.out.println("Warning: Unknown angle type, ignoring." + args[i]);
                                break;
                        }
                        break;

                    case "--start":
                    case "--initial":
                    case "-i":
                        try {
                            startValue = Double.parseDouble(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Non-double where double expected, aborting. " + args[i]);
                            System.exit(-1);
                        }
                        break;

                    case "--columns":
                    case "--cols":
                    case "--col":
                    case "-c":
                        try {
                            numberOfColumns = Integer.parseUnsignedInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Non-integer where integer expected, aborting. " + args[i]);
                            System.exit(-1);
                        }
                        break;

                    case "--help":
                    case "--info":
                    case "--usage":
                    case "-h":
                    case "-?":
                        printHelp();
                        System.exit(0);

                    case "--float":
                        appendF = "f";
                        break;

                    default:
                        System.out.println("Warning: unknown argument, ignoring. " + args[i]);
                        break;
                }
                i++;    // Next arg
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: argument flag missing value.");
            System.exit(-1);
        }

        // Make sure we have everything we need:
        if (function == null || totalSteps <= 0 || stopValue <= 0) {
            System.out.println("Error: you are probably missing some arguments, see below:");
            printHelp();
            System.exit(-1);
        }

        System.out.print("{\n ");
        int computationNumber = 0;
        int column = numberOfColumns + 1;
        while (computationNumber <= totalSteps) {
            if (--column == 0) {
                column = numberOfColumns;
                System.out.print("\n ");
            }

            double x = (computationNumber * (stopValue - startValue)) / totalSteps;
            String number = computeExpression(x, function, angleType);
            String paddedNumber;
            if (computationNumber == totalSteps)
                paddedNumber = String.format("%-25s", number + appendF);
            else
                paddedNumber = String.format("%-25s", number + appendF + ',');
            System.out.print(paddedNumber);

            computationNumber++;
        }
        System.out.println("\n}");
    }
}
