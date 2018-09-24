import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.*;

public class player19 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;
    private final int dimension = 10;
    public static boolean DEBUG = true;

    public player19() {
        rnd_ = new Random();
    }

    public void setSeed(long seed) {
        // Set seed of algortihms random process
        rnd_.setSeed(seed);
    }

    public void setEvaluation(ContestEvaluation evaluation) {
        // Set evaluation problem used in the run
        evaluation_ = evaluation;

        // Get evaluation properties
        Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

        // Do sth with property values, e.g. specify relevant settings of your algorithm
        if (isMultimodal) {
            // Do sth
        } else {
            // Do sth else
        }
    }

    public void run() {
        double[][] population;
        int populationSize = 100;
        // Run your algorithm here
        Initializations Inits = new Initializations(dimension);
        Variations Vars = new Variations();
        Selections Sels = new Selections();

        int evals = 0;
        // init population
        // calculate fitness
        population = Inits.initPopulation(populationSize, evaluation_, Initializations.RandomDistributions.NORMAL);
        while (evals < evaluations_limit_) {
            // Select parents
            Sels.sortbyColumn(population, Inits.solutionDimension);
            int[] parentsInd = Sels.parentSelection(population, evaluation_, 5, 2,
                    Initializations.RandomDistributions.NORMAL, Inits);
            // Apply crossover / mutation operators
            for (int parentInd : parentsInd) {
                Vars.rnd_swap(population[parentInd]);
            }
            // double child[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
            // Check fitness of unknown fuction
            Inits.updateFitness(population, evaluation_);
            // Double fitness = (double) evaluation_.evaluate(child);
            // Select survivors
            System.out.println("Best fitness value at evaluation " + Integer.toString(evals) + ": "
                    + Double.toString(Inits.maxScore));
            // System.out.println(Arrays.toString(population));
            evals++;

        }
        // double[][] mystring = new double[][] { // just an example matrix
        // { 2.0, 3.2, 2.0 }, { 1.0, 4.2, 1.0 }, { 9.2, 5.2, 9.2 }, {3.7, 2.9, 3.3} };
        // System.out.println("Original:" + Arrays.deepToString(mystring));// print the
        // original array
        // double[][] newstring = Sels.survSelection(mystring, 2); //keep only the best
        // 2 rows of 'mystring' based on the last column
        // System.out.println("New (keeps best 2):" + Arrays.deepToString(newstring));
        // //check if it works

        // // Test arr
        // double[] arr = {1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0};

        // // Functions takes arr and swaps 2 floats
        // Vars.rnd_swap(arr);

        // // check if swapped
        // System.out.println(Arrays.toString(arr));

    }

}
