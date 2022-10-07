import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
public class Calculator {

    private static final int ERR = -1;

    private static final String COMMA_DELIM = ",";

    private static final String SEP = "---";

    public static void main(String[] args){
        // Requires a file name argument
        if (args.length < 1) {
            System.out.println("Argument error");
            System.exit(ERR);
        }

        int levels = 0;

        List<List<Integer>> samples = new ArrayList<>();

        String filename = args[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            line = br.readLine();
            // Comma separated values
            String[] values = line.split(COMMA_DELIM);
            // Simple check for data
            levels = values.length;
            if (levels <= 0) {
                System.out.println("Sample dimensions are un-operable");
                System.exit(ERR);
            }
            // An array list for each column
            for (int i = 0; i < values.length; i++) {
                samples.add(new ArrayList<>());
            }
            // now read each line
            while (line != null) {
                values = line.split(COMMA_DELIM);
                // Each new line must have the same dimensions
                if (values.length != levels) {
                    System.out.println("Sample dimensions are inconsistent");
                    System.exit(ERR);
                }
                // Get the corresponding list for each column, and add the value
                for (int i = 0; i < values.length; i++) {
                    List<Integer> sample = samples.get(i);
                    sample.add(Integer.parseInt(values[i]));
                }
                line = br.readLine();
            }
            br.close();
        } catch(Exception ioe) {
            System.out.println(ioe.getMessage());
            System.exit(ERR);
        }
        // Print out data to the user so, we know it's read in correctly
        System.out.println(SEP + " Raw Data " + SEP);
        int sampleLength = samples.get(0).size();
        for (int i = 0; i < sampleLength; i++) {
            System.out.print(i + "\t\t\t");
            for (List<Integer> sample : samples) {
                System.out.print(sample.get(i) + "\t");
            }
            System.out.println();
        }

        int m = levels;

        System.out.println(SEP + " Calculated Data " + SEP);
        // List of column means
        List<Double> means = new ArrayList<>();
        System.out.print("Means: \t\t\t");
        for (int i = 0; i < samples.size(); i++) {
            means.add(mean(samples.get(i)));
            System.out.print(roundSigFigs(means.get(i)) + "\t");
        }
        System.out.println();

        // Calculate sum of squares for each column
        List<Double> sumsOfSquares = new ArrayList<>();
        System.out.print("Sum of squares: \t");
        for (int i = 0; i < samples.size(); i++) {
            sumsOfSquares.add(sumOfSquares(samples.get(i), means.get(i)));
            System.out.print(roundSigFigs(sumsOfSquares.get(i)) + "\t");
        }
        System.out.println();


        // Calculate data 

        // Get all samples as one list
        List<Integer> allSamples = unwrapSamples(samples);
        int n = allSamples.size();
        double totalMean = mean(allSamples);
        double totalSumOfSquares = sumOfSquares(allSamples, totalMean);

        double error = sumList(sumsOfSquares);

        double effect = totalSumOfSquares - error;

        // Degrees of freedom
        int df_error = n - m;

        int df_effect = m - 1;


        double ms_error = error / df_error;

        double ms_effect = effect / df_effect;
        //


        // Critical value will be in accordance with the F distribution and the chosen alpha level
        double f_ratio = ms_effect / ms_error;

        // Print data

        System.out.println("Total sample mean: \t" + roundSigFigs(totalMean));

        System.out.println("SS total: \t\t" + roundSigFigs(totalSumOfSquares));

        System.out.println("SS error: \t\t" + roundSigFigs(error));

        System.out.println("SS effect: \t\t" + roundSigFigs(effect));

        System.out.println("Participants (n): \t" + n);

        System.out.println("Groups (m):\t\t" + m);


        System.out.println("DoF (error): \t\t" + df_error);

        System.out.println("DoF (effect): \t\t" + df_effect);

        System.out.println("MS (error): \t\t" + roundSigFigs(ms_error));

        System.out.println("MS (effect): \t\t" + roundSigFigs(ms_effect));

        System.out.println("F ratio: \t\t" + roundSigFigs(f_ratio));

        System.out.println("\n(Values are truncated to 3 s.f. when printing. Values were not truncated when calculating)");

    }

    /**
     * Sum the elements in the list of double values
     * @param list of floating point numbers
     * @return the sum
     */
    private static double sumList(List<Double> list) {
        double sum = 0.0;
        for (Double aDouble : list) {
            sum += aDouble;
        }
        return sum;
    }

    /**
     * Take a list of lists, and create one large list containing every element.
     * @param samples list of lists
     * @return a large list of all samples
     */
    private static List<Integer> unwrapSamples(List<List<Integer>> samples) {

        List<Integer> allSamples = new ArrayList<>();
        for (List<Integer> nextSample : samples) {
            allSamples.addAll(nextSample);
        }
        return allSamples;
    }

    /**
     * Taking a list, calculate the residual from the mean and square it, summing each element we do this for
     * @param sample list
     * @param mean mean of the samples
     * @return the sum of squares
     */
    private static double sumOfSquares(List<Integer> sample, double mean) {
        double sum = 0.0;
        for (Integer integer : sample) {
            double residual = integer - mean;
            sum += Math.pow(residual, 2);
        }

        return sum;
    }


    /**
     * Calculate the mean of a given sample
     * @param sample
     * @return
     */
    private static double mean(List<Integer> sample) {
        int total = 0;
        for (Integer integer : sample) {
            total += integer;
        }
        return ((double)total / (double)sample.size());
    }

    /**
     * Round to 3 significant figures
     * @param val number to round
     * @return a string rep of the floating point number
     */
    private static String roundSigFigs(double val) {
        return String.format("%.3f", val);
    }

}
