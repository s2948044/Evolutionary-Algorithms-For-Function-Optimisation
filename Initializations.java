import java.util.*;
import org.vu.contest.ContestEvaluation;

public class Initializations {
    public double maxScore;
    private ContestEvaluation evaluation_;
    private Configs cfgs;

    public Initializations(Configs cfgs, ContestEvaluation evaluation_) {
        this.evaluation_ = evaluation_;
        this.cfgs = cfgs;
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
            double[] tempPop = Arrays.copyOfRange(population[i], 0, this.cfgs.getDimension());
            // System.out.println(this.cfgs.getDimension());
            double tempEval = (double) this.evaluation_.evaluate(tempPop);
            player19.evals++;

            population[i][this.cfgs.getDimension()] = tempEval;

            if (this.cfgs.getDEBUG()) {
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
        double[][] population = new double[this.cfgs.getPopulationSize()][this.cfgs.getDimension()
                + this.cfgs.getAdditionalDimension()];

        for (int i = 0; i < this.cfgs.getPopulationSize(); i++) {
            for (int j = 0; j < this.cfgs.getDimension(); j++) {
                switch (rand) {
                case UNIFORM:
                    population[i][j] = this.cfgs.getLowerBound()
                            + (this.cfgs.getUpperBound() - this.cfgs.getLowerBound()) * new Random().nextDouble();
                    break;
                case NORMAL:
                    population[i][j] = new Random().nextGaussian() * this.cfgs.getInitSigma();
                    if (population[i][j] > this.cfgs.getUpperBound()) {
                        population[i][j] = this.cfgs.getUpperBound();
                    } else if (population[i][j] < this.cfgs.getLowerBound()) {
                        population[i][j] = this.cfgs.getLowerBound();
                    }
                    break;
                }
            }
        }
        return population;
    }
}
