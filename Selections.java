import java.util.*;
import org.vu.contest.ContestEvaluation;

public class Selections {
    public Selections() {
    }

    /**
     * Sort the population by fitness value at the 11th dimension.
     * 
     * @param population
     * @param colToSort  Index of column to sort on.
     */
    public void sortbyColumn(double population[][], int colToSort) {
        // Using built-in sort function Arrays.sort
        Arrays.sort(population, new Comparator<double[]>() {

            @Override
            // Compare values according to columns
            public int compare(final double[] entry1, final double[] entry2) {

                // To sort in descending order
                if (entry1[colToSort] < entry2[colToSort])
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

    /**
     * Parent selection based on Elitism.
     * 
     * @param population
     * @param evaluation_
     * @param randomSize    Number of ranomly selected individuals.
     * @param intensionSize Number of finally picked best parents.
     * @param rand          Specified random distribution.
     * @param Inits
     * @return
     */
    public int[] parentSelection_Elitism(double[][] population, ContestEvaluation evaluation_, int randomSize,
            int intensionSize, Initializations.RandomDistributions rand, Initializations Inits) {
        // Extract the fitness values per individuals in the population.
        double[] fitnessValues = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            fitnessValues[i] = population[i][Inits.solutionDimension];
        }

        // Use roulette wheel selection to pick (randomSize) of individuals out of the
        // population and select the best (intensionSize) of them.
        int[] chosenInd = new int[randomSize];
        for (int i = 0; i < randomSize; i++) {
            // chosenInd[i] = rouletteWheelSelection(fitnessValues, rand);
            chosenInd[i] = new Random().nextInt(population.length - 1);
            boolean overlapping = true;
            while (overlapping) {
                overlapping = false;
                for (int j = 0; j < i; j++) {
                    if (chosenInd[i] == chosenInd[j]) {
                        // chosenInd[i] = rouletteWheelSelection(fitnessValues, rand);
                        chosenInd[i] = new Random().nextInt(population.length - 1);
                        overlapping = true;
                        break;
                    }
                }
            }
        }
        Arrays.sort(chosenInd);
        int[] parentsInd = new int[intensionSize];
        for (int i = 0; i < intensionSize; i++) {
            parentsInd[i] = chosenInd[i];
        }
        return parentsInd;
    }

    /**
     * Survivor selection based on Elitism.
     * 
     * @param population
     * @param intensionSize Number of finally selected individuals.
     * @param Inits
     * @return Purged population.
     */
    public double[][] survSelection_Elitism(double[][] population, int intensionSize, Initializations Inits) {
        sortbyColumn(population, Inits.solutionDimension); // sort the current matrix of the population based on the
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
