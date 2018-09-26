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
     * @param rand Specified random distribution.
     * @return double type matrix of size (populationSize x (vectorDimension + 1))
     */
    public double[][] initPopulation(Initializations.RandomDistributions rand) {
        double[][] population = new double[_Cfgs.getpopulationSize()][_Cfgs.getdimension()
                + _Cfgs.getadditionalDimension()];

        for (int i = 0; i < _Cfgs.getpopulationSize(); i++) {
            for (int j = 0; j < _Cfgs.getdimension(); j++) {
                switch (rand) {
                case UNIFORM:
                    population[i][j] = _Cfgs.getlowerBound()
                            + (_Cfgs.getupperBound() - _Cfgs.getlowerBound()) * new Random().nextDouble();
                    break;
                case NORMAL:
                    population[i][j] = new Random().nextGaussian() * _Cfgs.getinitSigma();
                    if (population[i][j] > _Cfgs.getupperBound()) {
                        population[i][j] = _Cfgs.getupperBound();
                    } else if (population[i][j] < _Cfgs.getlowerBound()) {
                        population[i][j] = _Cfgs.getlowerBound();
                    }
                    break;
                }
            }
        }
        return population;
    }
}
