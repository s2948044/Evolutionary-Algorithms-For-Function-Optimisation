import java.util.*;
import org.vu.contest.ContestEvaluation;

public class Initializations {
    public double maxScore;
    private ContestEvaluation _evaluation;
    private Configs _Cfgs;

    public Initializations(Configs Cfgs, ContestEvaluation evaluation_) {
        _evaluation = evaluation_;
        _Cfgs = Cfgs;
    }

    public enum RandomDistributions {
        UNIFORM, NORMAL;
    }

    /**
     * Update the fitness values for each individuals in the population.
     * 
     * @param population
     * @param _evaluation
     */
    public void updateFitness(double[][] population) {
        maxScore = -1;
        for (int i = 0; i < population.length; i++) {
            double[] tempPop = Arrays.copyOfRange(population[i], 0, _Cfgs.getdimension());
            double tempEval = (double) _evaluation.evaluate(tempPop);
            player19.evals++;

            population[i][_Cfgs.getdimension()] = tempEval;

            if (_Cfgs.getDEBUG()) {
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
     * @param _evaluation    Evaluation object from the contest library.
     * @return double type matrix of size (populationSize x (vectorDimension + 1))
     */
    public double[][] initPopulation(Initializations.RandomDistributions rand) {
        double[][] population = new double[_Cfgs.getpopulationSize()][_Cfgs.getdimension() + 1];

        for (int i = 0; i < _Cfgs.getpopulationSize(); i++) {
            for (int j = 0; j < _Cfgs.getdimension(); j++) {
                switch (rand) {
                case UNIFORM:
                    population[i][j] = -5 + 10 * new Random().nextDouble();
                    break;
                case NORMAL:
                    population[i][j] = new Random().nextGaussian() * 2.5;
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
