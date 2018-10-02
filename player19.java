import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.*;

public class player19 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;

    public static int evals;

    public player19() {
        rnd_ = new Random();
    }

    public void setSeed(long seed) {
        // Set seed of algortihms random process
        rnd_.setSeed(seed);
    }

    public static void resetEvals() {
        evals = 0;
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
        // Run your algorithm here
        Configs cfgs = new Configs();
        Initializations Inits = new Initializations(cfgs, evaluation_);
        Variations Vars = new Variations(cfgs);
        Selections Sels = new Selections(cfgs);

        cfgs.initParams();
        // init population
        // calculate fitness
        double[][] population;
        population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
        resetEvals();
        Inits.updateFitness(population);
        while (evals < evaluations_limit_) {
            if (evals % cfgs.getPopulationSize() == 0) {
                System.out.println("Best fitness value at evaluation " + Integer.toString(evals) + ": "
                        + Double.toString(Inits.maxScore));
            }
            // Select parents
            Sels.sortbyColumn(population, cfgs.getDimension());
            int[] parentsInd = Sels.parentSelection_RankedBased(population);
            // int[] parentsInd = Sels.parentSelection_Elitism(population,
            // Initializations.RandomDistributions.UNIFORM);
            // Apply crossover
            for (int i = 0; i < cfgs.getParentSelected(); i = i + 2) {
                // population = Vars.order1CrossOver(population, population[parentsInd[i]],
                // population[parentsInd[i + 1]]);
                // population = Vars.singleArithmeticCrossOver(population,
                // population[parentsInd[i]],
                // population[parentsInd[i + 1]]);
                // population = Vars.simpleArithmeticCrossOver(population,
                // population[parentsInd[i]],
                // population[parentsInd[i + 1]]);
                // population = Vars.blendCrossOver(population, population[parentsInd[i]],
                // population[parentsInd[i + 1]]);
                population = Vars.wholeArithmeticCrossOver(population, population[parentsInd[i]],
                        population[parentsInd[i + 1]]);
            }
            // Apply mutation
            for (int i = cfgs.getPopulationSize(); i < cfgs.getPopulationSize() + cfgs.getParentSelected(); i++) {
                if (new Random().nextInt((int) (1 / cfgs.getMutationRate())) == 0) {
                    // Vars.rnd_swap(population[i]);
                    // Vars.customizedMutation(population[i]);
                    // Vars.nonUniformMutation(population[i]);
                    // Vars.singleUncorrelatedMutation(population[i]);
                    // Vars.multiUncorrelatedMutation(population[i]);
                    Vars.correlatedMutation(population[i]);
                }
            }
            // Check fitness of unknown fuction
            for (int i = 0; i < cfgs.getParentSelected(); i++) {
                double[] tempPop = Arrays.copyOfRange(population[cfgs.getPopulationSize() + i], 0, cfgs.getDimension());
                double tempEval = (double) evaluation_.evaluate(tempPop);
                population[cfgs.getPopulationSize() + i][cfgs.getDimension()] = tempEval;
                if (cfgs.getDEBUG()) {
                    if (tempEval >= Inits.maxScore) {
                        Inits.maxScore = tempEval;
                    }
                }
                evals++;
            }
            // Select survivors
            population = Sels.survSelection_Elitism(population);
        }
        System.out.println(
                "Best fitness value at evaluation " + Integer.toString(evals) + ": " + Double.toString(Inits.maxScore));
        System.out.println(Arrays.deepToString(population));
    }

}

// For backup:

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
