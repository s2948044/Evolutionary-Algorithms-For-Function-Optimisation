import java.util.Arrays;
import java.util.Random;
import org.vu.contest.ContestEvaluation;

public class Initializations {
    private final int solutionDimension;

    public Initializations(int dimension) {
        solutionDimension = dimension;
    }

    /**
     * Population initialization with 1 additional dimension to store the fitness
     * values.
     * 
     * @param populationSize Total number of individuaals in the popluation.
     * @param evaluation_    Evaluation object from the contest library.
     * @return double type matrix of size (populationSize x (vectorDimension + 1))
     */
    public double[][] initPopulation(int populationSize, ContestEvaluation evaluation_) {
        double[][] population = new double[populationSize][solutionDimension + 1];

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < solutionDimension + 1; j++) {
                // TODO: Extend with different random distributions.
                population[i][j] = -5 + 10 * new Random().nextDouble();
            }
            population[i][solutionDimension] = (double) evaluation_
                    .evaluate(Arrays.copyOfRange(population[i], 0, solutionDimension));
        }

        return population;
    }
}