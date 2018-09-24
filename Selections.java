import java.util.*;
import org.vu.contest.ContestEvaluation;

public class Selections {
    public Selections() {
    }

    public static void sortbyColumn(double population[][], int colToSort) {
        // Using built-in sort function Arrays.sort
        Arrays.sort(population, new Comparator<double[]>() {

            @Override
            // Compare values according to columns
            public int compare(final double[] entry1, final double[] entry2) {

                // To sort in descending order
                if (entry1[colToSort - 1] < entry2[colToSort - 1])
                    return 1;
                else
                    return -1;
            }
        });
    }

    /**
     * Roulette Wheel Selection.
     * 
     * @param fitnessValues Array of fitness values corresponding to the population.
     * @return Index of the selected individual.
     */

    public int rouletteWheelSelection(double[] fitnessValues, Initializations.RandomDistributions rand) {
        // Sum up the fitnessValues of all the selectees.
        double fitnessSum = 0;
        for (double fitness : fitnessValues) {
            fitnessSum += fitness;
        }

        // Compute the corresponding probabilities.
        double[] probs = new double[fitnessValues.length];
        for (int i = 0; i < probs.length; i++) {
            probs[i] = fitnessValues[i] / fitnessSum;
        }

        // Init a random probability and return its corresponding index.
        double prob = 0;
        switch (rand) {
        case NORMAL:
            prob = new Random().nextDouble();
            break;
        case UNIFORM:
            prob = new Random().nextGaussian();
            break;
        }

        double probSum = 0;
        int i = 0;
        while (prob - probSum > 0) {
            probSum += probs[i];
            i++;
        }
        return i - 1;
    }

    public double[][] parentSelection(double[][] population, ContestEvaluation evaluation_, int randomSize,
            int intensionSize, Initializations.RandomDistributions rand) {
        // Extract the fitness values per individuals in the population.
        double[] fitnessValues = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            fitnessValues[i] = (double) evaluation_
                    .evaluate(Arrays.copyOfRange(population[i], 0, population[0].length - 1));
        }

        // Use roulette wheel selection to pick (randomSize) of individuals out of the
        // population and select the best (intensionSize) of them.
        int i = 0;
        int[] chosenInd = new int[randomSize];
        outer: while (i < randomSize) {
            chosenInd[i] = rouletteWheelSelection(fitnessValues, rand);
            for (int j = 0; j < i; j++) {
                if (chosenInd[i] == chosenInd[j]) {
                    continue outer;
                }
            }
            i++;
        }
        double[][] chosen = new double[randomSize][population[0].length];
        i = 0;
        for (int j : chosenInd) {
            chosen[i] = Arrays.copyOf(population[j], population[j].length);
            i++;
        }
        return survSelection(chosen, intensionSize);
    }

    public double[][] survSelection(double[][] population, int intensionSize) {
        sortbyColumn(population, population[0].length); // sort the current matrix of the population based on the
                                                        // fitness stored at the last column
        double[][] new_population = new double[intensionSize][population[0].length]; // create a new matrix for storing
                                                                                     // only the top numbers of
                                                                                     // individuals
        for (int i = 0; i < intensionSize; i++) {
            for (int j = 0; j < population[0].length; j++) {
                new_population[i][j] = population[i][j]; // copy from the old matrix to the new matrix
            }
        }
        return new_population;
    }
}
