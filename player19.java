import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.*;

public class player19 implements ContestSubmission {
    Random rnd_;
    ContestEvaluation evaluation_;
    private int evaluations_limit_;

    public static int evals;
    private boolean isMultimodal;
    private boolean hasStructure;
    private boolean isSeparable;

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
        Configs cfgs = new Configs();
        // Set evaluation problem used in the run
        evaluation_ = evaluation;

        // Get evaluation properties
        Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
        // Property keys depend on specific evaluation
        // E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));
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
        if (!isMultimodal) {
            if (!hasStructure) {
                if (!isSeparable) {
                    // set the customised parameters (if necessary) for BentCigar
                    cfgs.setPopulationSize(500);
                    cfgs.setTournamentSize(100);
                    cfgs.setParentSelected(250);
                    cfgs.setMutationRate(0.8);
                    cfgs.setMixingFactor(0.5);
                    cfgs.setOverallMutationCoefficient(0.01);
                    cfgs.setSecondaryMutationCoefficient(0.01);
                    cfgs.setInitialStepSize(0.1);
                    cfgs.setMutationStepSizeBound(0.01);
                }
            }
        }
        if (isMultimodal) {
            if (!hasStructure) {
                if (!isSeparable) {
                    // set the customised parameters (if necessary) for KatsuuraEvaluation
                    cfgs.setPopulationSize(2000);
                    cfgs.setTournamentSize(200);
                    cfgs.setParentSelected(1000);
                    cfgs.setInitSigma(3);
                    cfgs.setMutationRate(0.5);
                    cfgs.setMixingFactor(0.5);
                    cfgs.setOverallMutationCoefficient(0.003);
                    cfgs.setSecondaryMutationCoefficient(0.007);
                    cfgs.setInitialStepSize(0.01);
                    cfgs.setMutationStepSizeBound(0.001);
                }
            }
            if (hasStructure) {
                if (!isSeparable) {
                    // set the customised parameters (if necessary) SchaffersEvaluation
                    cfgs.setPopulationSize(2000);
                    cfgs.setTournamentSize(100);
                    cfgs.setParentSelected(1000);
                    cfgs.setMutationRate(0.8);
                    cfgs.setMixingFactor(0.7);
                    cfgs.setOverallMutationCoefficient(0.3);
                    cfgs.setSecondaryMutationCoefficient(0.1);
                    cfgs.setInitialStepSize(1);
                    cfgs.setMutationStepSizeBound(0.1);
                }
            }
        }
        population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < cfgs.getDimension(); j++) {
                // System.out.print(population[i][j] + " ");
                if (population[i][j] > 5 || population[i][j] < -5) {
                    System.out.print(population[i][j] + " ");
                    System.out.println("Warning!"); // check if any value goes out of [-5,5]
                }
            }
        }
        resetEvals();
        Inits.updateFitness(population);
        int fitnessCounter = 0;
        double pastHighest;
        double currentHighest;
        while (evals < evaluations_limit_) {
            pastHighest = Inits.maxScore;
            if (evals % cfgs.getPopulationSize() == 0) {
                System.out.println("Best fitness value at evaluation " + Integer.toString(evals) + ": "
                        + Double.toString(Inits.maxScore));
            }
            for (int i = 0; i < population.length; i++) {
                for (int j = 0; j < cfgs.getDimension(); j++) {
                    // System.out.print(population[i][j] + " ");
                    if (population[i][j] > 5 || population[i][j] < -5) {
                        System.out.print(population[i][j] + " ");
                        System.out.println("Warning!"); // check if any value goes out of [-5,5]
                    }
                }
            }
            // Select parents
            Sels.sortbyColumn(population, population[0].length - 1);

            int[] parentsInd = Sels.parentSelection_Tournament(population);

            // Apply crossover
            for (int i = 0; i < cfgs.getParentSelected(); i = i + 2) {
                if (!isMultimodal) {
                    if (!hasStructure) {
                        if (!isSeparable) {
                            // set the crossover operator for BentCigar
                            population = Vars.simpleArithmeticCrossOver(population, population[parentsInd[i]],
                                    population[parentsInd[i + 1]]);
                        }
                    }
                }
                if (isMultimodal) {
                    if (!hasStructure) {
                        if (!isSeparable) {
                            // set the crossover operator for KatsuuraEvaluation
                            population = Vars.wholeArithmeticCrossOver(population, population[parentsInd[i]],
                                    population[parentsInd[i + 1]]);
                        }
                    }
                    if (hasStructure) {
                        if (!isSeparable) {
                            // set the crossover operator for SchaffersEvaluation
                            population = Vars.simpleArithmeticCrossOver(population, population[parentsInd[i]],
                                    population[parentsInd[i + 1]]);
                        }
                    }
                }
            }
            // Apply mutation
            for (int i = cfgs.getPopulationSize(); i < cfgs.getPopulationSize() + cfgs.getParentSelected(); i++) {
                if (new Random().nextDouble() < cfgs.getMutationRate()) {
                    if (!isMultimodal) {
                        if (!hasStructure) {
                            if (!isSeparable) {
                                // set the mutation operator for BentCigar
                                Vars.correlatedMutation(population[i]);
                            }
                        }
                    }
                    if (isMultimodal) {
                        if (!hasStructure) {
                            if (!isSeparable) {
                                // set the mutation operator for KatsuuraEvaluation
                                Vars.correlatedMutation(population[i]);
                            }
                        }
                        if (hasStructure) {
                            if (!isSeparable) {
                                // set the mutation operator for SchaffersEvaluation
                                Vars.correlatedMutation(population[i]);
                            }
                        }
                    }
                }
            }
            // Check fitness of unknown fuction
            for (int i = 0; i < cfgs.getParentSelected(); i++) {
                double[] tempPop = Arrays.copyOfRange(population[cfgs.getPopulationSize() + i], 0, cfgs.getDimension());
                double tempEval = 0.0;
                try {
                    tempEval = (double) evaluation_.evaluate(tempPop);
                } catch (NullPointerException e) {
                    break;
                }

                population[cfgs.getPopulationSize() + i][population[i].length - 1] = tempEval;
                if (cfgs.getDEBUG()) {
                    if (tempEval >= Inits.maxScore) {
                        Inits.maxScore = tempEval;
                    }
                }
                evals++;
            }
            // Select survivors
            population = Sels.survSelection_Elitism(population);

            try {
                currentHighest = Inits.maxScore;
                if (pastHighest == currentHighest) {
                    fitnessCounter++;
                } else {
                    fitnessCounter = 0;
                }
                if (isMultimodal) {
                    if (hasStructure) {
                        if (!isSeparable) {
                            // SchaffersEvaluation
                            if (fitnessCounter >= 2 && currentHighest < 6) {
                                fitnessCounter = 0;
                                population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
                                Inits.updateFitness(population);
                            }
                            if (fitnessCounter >= 4 && currentHighest < 7) {
                                fitnessCounter = 0;
                                population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
                                Inits.updateFitness(population);
                            }
                            if (fitnessCounter >= 6 && currentHighest < 8) {
                                fitnessCounter = 0;
                                population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
                                Inits.updateFitness(population);
                            }
                            if (fitnessCounter >= 8 && currentHighest < 9) {
                                fitnessCounter = 0;
                                population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
                                Inits.updateFitness(population);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                break;
            }
        }
        System.out.println(
                "Best fitness value at evaluation " + Integer.toString(evals) + ": " + Double.toString(Inits.maxScore));
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < cfgs.getDimension(); j++) {
                // System.out.print(population[i][j] + " ");
                if (population[i][j] > 5 || population[i][j] < -5) {
                    System.out.print(population[i][j] + " ");
                    System.out.println("Warning!"); // check if any value goes out of [-5,5]
                }
            }
        }
    }

}
