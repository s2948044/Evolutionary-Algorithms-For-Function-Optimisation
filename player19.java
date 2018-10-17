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
    public static double overallMaxScore = -1.0;

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
                    cfgs.setPopulationSize(100);
                    cfgs.setTournamentSize(10);
                    cfgs.setParentSelected(60);
                    cfgs.setMutationRate(0.8);
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
                    cfgs.setInitialStepSize(1);
                    cfgs.setMutationStepSizeBound(0.1);
                }
            }
        }
        population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
        resetEvals();
        Inits.updateFitness(population);
        int fitnessCounter = 0;
        double pastHighest;
        double currentHighest;
        while (evals < evaluations_limit_) {
            pastHighest = Inits.maxScore;
            if (evals % cfgs.getPopulationSize() == 0) {
                cfgs.append_xdata(overallMaxScore);
            }
            // Select parents
            Sels.sortbyColumn(population, population[0].length - 1);

            int[] parentsInd = Sels.parentSelection_Tournament(population);

            // Apply crossover
            for (int i = 0; i < cfgs.getParentSelected(); i = i + 2) {
                switch (cfgs.getXoverChoice()) {
                case 1:
                    population = Vars.singleArithmeticCrossOver(population, population[parentsInd[i]],
                            population[parentsInd[i + 1]]);
                    break;

                case 2:
                    population = Vars.simpleArithmeticCrossOver(population, population[parentsInd[i]],
                            population[parentsInd[i + 1]]);
                    break;

                case 3:
                    population = Vars.wholeArithmeticCrossOver(population, population[parentsInd[i]],
                            population[parentsInd[i + 1]]);
                    break;

                case 4:
                    population = Vars.blendCrossOver(population, population[parentsInd[i]],
                            population[parentsInd[i + 1]]);
                    break;
                }
            }
            // Apply mutation
            for (int i = cfgs.getPopulationSize(); i < cfgs.getPopulationSize() + cfgs.getParentSelected(); i++) {
                if (new Random().nextDouble() < cfgs.getMutationRate()) {
                    switch (cfgs.getMutationChoice()) {
                    case 1:
                        Vars.rnd_swap(population[i]);
                        break;

                    case 2:
                        Vars.uniformMutation(population[i]);
                        break;

                    case 3:
                        Vars.nonUniformMutation(population[i]);
                        break;

                    case 4:
                        Vars.customizedMutation(population[i]);
                        break;

                    case 5:
                        Vars.singleUncorrelatedMutation(population[i]);
                        break;

                    case 6:
                        Vars.multiUncorrelatedMutation(population[i]);
                        break;

                    case 7:
                        Vars.correlatedMutation(population[i]);
                        break;
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
                    cfgs.append_mbfdata(overallMaxScore);
                    cfgs.make_data_jstring(cfgs.get_mbfdata());
                    String json = cfgs.get_data_jstring();
                    System.out.println(json);
                    System.exit(1);
                }

                population[cfgs.getPopulationSize() + i][population[i].length - 1] = tempEval;
                if (cfgs.getDEBUG()) {
                    if (tempEval >= Inits.maxScore) {
                        Inits.maxScore = tempEval;
                    }
                    if (tempEval >= overallMaxScore) {
                        overallMaxScore = tempEval;
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
                }
                else{
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
                cfgs.append_mbfdata(overallMaxScore);
                cfgs.make_data_jstring(cfgs.get_mbfdata());
                String json = cfgs.get_data_jstring();
                System.out.println(json);
                System.exit(1);
            }
        }

        // For details:
        // cfgs.make_data_jstring(cfgs.get_xdata());

        // For MBF statistics:
        cfgs.append_mbfdata(overallMaxScore);
        cfgs.make_data_jstring(cfgs.get_mbfdata());

        String json = cfgs.get_data_jstring();
        System.out.println(json);
    }

}
