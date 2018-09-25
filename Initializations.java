import java.util.*;
import org.vu.contest.ContestEvaluation;

import javafx.stage.Popup;

public class Initializations {
    public final int solutionDimension;
    public double maxScore;

    public Initializations(int dimension) {
        solutionDimension = dimension;
    }

    public enum RandomDistributions {
        UNIFORM, NORMAL;
    }

    public void updateFitness(double[][] population, ContestEvaluation evaluation_) {
        maxScore = -1;
        for (int i = 0; i < population.length; i++) {

            double[] tempPop = Arrays.copyOfRange(population[i], 0, solutionDimension);
            if (i <= 2) {
                System.out.print("Row " + Integer.toString(i) + " : ");
                System.out.println(Arrays.toString(population[i]));
            }
            double tempEval = (double) evaluation_.evaluate(tempPop);
            population[i][solutionDimension] = tempEval;

            if (player19.DEBUG) {
                if (tempEval >= maxScore) {
                    maxScore = tempEval;
                }
            }
        }
    }

    // public void updateIndex(double[][] popluation) {
    // for (int i = 0; i < popluation.length; i++) {
    // popluation[i][solutionDimension + 1] = i;
    // }
    // }

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
            population[i][solutionDimension] = (double) evaluation_
                    .evaluate(Arrays.copyOfRange(population[i], 0, solutionDimension));
        }
        return population;
    }
}
