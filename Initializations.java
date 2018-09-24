import java.util.*;
import org.vu.contest.ContestEvaluation;

public class Initializations {
    public final int solutionDimension;

    public Initializations(int dimension) {
        solutionDimension = dimension;
    }

    public enum RandomDistributions {
        UNIFORM, NORMAL;
    }

    public void updateFitness(double[][] population, ContestEvaluation evaluation_) {
        for (int i = 0; i < population.length; i++) {
            population[i][solutionDimension] = (double) evaluation_
                    .evaluate(Arrays.copyOfRange(population[i], 0, solutionDimension));
        }
    }

    /**
     * Population initialization with 1 additional dimension to store the fitness
     * values.
     * 
     * @param populationSize Total number of individuaals in the popluation.
     * @param evaluation_    Evaluation object from the contest library.
     * @return double type matrix of size (populationSize x (vectorDimension + 1))
     */
    public double[][] initPopulation(int populationSize, ContestEvaluation evaluation_,
            Initializations.RandomDistributions rand) {
        double[][] population = new double[populationSize][solutionDimension + 1];

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < solutionDimension + 1; j++) {
                switch (rand) {
                case UNIFORM:
                    population[i][j] = -5 + 10 * new Random().nextDouble();
                    break;
                case NORMAL:
                    population[i][j] = -5 + 10 * new Random().nextGaussian();
                    break;
                }
            }
            population[i][solutionDimension] = (double) evaluation_
                    .evaluate(Arrays.copyOfRange(population[i], 0, solutionDimension));
        }

        return population;
    }
}
