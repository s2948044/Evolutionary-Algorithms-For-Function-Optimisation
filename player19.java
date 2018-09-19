import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.Arrays;

public class player19 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;
    private final int dimension = 10;

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
        population = Inits.initPopulation(populationSize, evaluation_);
        // calculate fitness
        while (evals < evaluations_limit_) {
            // Select parents
            // Apply crossover / mutation operators
            // double child[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
            // Check fitness of unknown fuction
            // Double fitness = (double) evaluation_.evaluate(child);
            evals++;
            // Select survivors
        }
        Double[][] mystring = new Double[][]{ //just an example of sorting by a column
			{2.0,3.2},
			{1.0,4.2},
			{9.2,5.2},
		};
		Arrays.sort(mystring, (Double[] c1, Double[] c2) -> c1[0].compareTo(c2[0])); //sorting by a column
		System.out.println(Arrays.deepToString(mystring));//print the array
    }

}
