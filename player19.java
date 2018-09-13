import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player19 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;

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
        // Run your algorithm here

        int evals = 0;
        // init population
        // calculate fitness
        while (evals < evaluations_limit_) {
            // Select parents
            // Apply crossover / mutation operators
            // double child[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
            double child[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(child);
            System.out.println("Selected " + rouletteWheelSelection(child));
            evals++;
            // Select survivors
        }

    }

    /**
     * Roulette Wheel Selection.
     * 
     * @param fitnessValues Array of fitness values corresponding to the population.
     * @return Index of the selected individual.
     */
    private int rouletteWheelSelection(double[] fitnessValues) {
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
        double prob = new Random().nextDouble();
        double probSum = 0;
        int i = 0;
        while (prob - probSum > 0) {
            probSum += probs[i];
            i++;
        }
        return i - 1;
    }
}
