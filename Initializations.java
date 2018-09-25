import java.util.*;
import org.vu.contest.ContestEvaluation;

public class Initializations {
    public final int solutionDimension;
    public double maxScore;

    public Initializations(int dimension) {
        solutionDimension = dimension;
    }

    public enum RandomDistributions {
        UNIFORM, NORMAL;
    }

    /**
     * Update the fitness values for each individuals in the population.
     * 
     * @param population
     * @param evaluation_
     */
    public void updateFitness(double[][] population, ContestEvaluation evaluation_) {
        maxScore = -1;
        for (int i = 0; i < population.length; i++) {
            double[] tempPop = Arrays.copyOfRange(population[i], 0, solutionDimension);
            double tempEval = (double) evaluation_.evaluate(tempPop);
            player19.evals++;

            population[i][solutionDimension] = tempEval;

            if (player19.DEBUG) {
                if (tempEval >= maxScore) {
                    maxScore = tempEval;
                }
            }
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
            for (int j = 0; j < solutionDimension; j++) {
                switch (rand) {
                case UNIFORM:
                    population[i][j] = -5 + 10 * new Random().nextDouble();
                    break;
                case NORMAL:
                    population[i][j] = new Random().nextGaussian() * 2;
                    if (population[i][j] > 5) {
                        population[i][j] = 5;
                    } else if (population[i][j] < -5) {
                        population[i][j] = -5;
                    }
                    break;
                }
            }
        }
        return population;
    }
}
