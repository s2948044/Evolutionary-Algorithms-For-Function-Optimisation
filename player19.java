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

		// Do sth with property values, e.g. specify relevant settings of your algorithm
		// if (!isMultimodal) {
		// if (!hasStructure){
		// if (!isSeparable){
		// // BentCigar
		// }
		// }
		// }
		// if (isMultimodal) {
		// if (!hasStructure){
		// if (!isSeparable){
		// // KatsuuraEvaluation
		// }
		// }
		// if (hasStructure){
		// if (!isSeparable){
		// // SchaffersEvaluation
		// }
		// }
		// }
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
					System.out.println("BentCigar");
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
					System.out.println("KatsuuraEvaluation");
					cfgs.setPopulationSize(500);
					cfgs.setTournamentSize(20);
					cfgs.setParentSelected(250);
					cfgs.setMutationRate(0.5);
				}
			}
			if (hasStructure) {
				if (!isSeparable) {
					// set the customised parameters (if necessary) SchaffersEvaluation
					System.out.println("SchaffersEvaluation");
					cfgs.setPopulationSize(2000);
					cfgs.setTournamentSize(100);
					cfgs.setParentSelected(1000);
					cfgs.setMutationRate(0.8);
				}
			}
		}
		population = Inits.initPopulation(Initializations.RandomDistributions.NORMAL);
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < population[0].length - 1; j++) {
				// System.out.print(population[i][j] + " ");
				if (population[i][j] > 5 || population[i][j] < -5) {
					System.out.print(population[i][j] + " ");
					System.out.println("Warning!"); // check if any value goes out of [-5,5]
				}
			}
		}
		// System.out.println("Population Size " + population.length);
		resetEvals();
		Inits.updateFitness(population);
		while (evals < evaluations_limit_) {
			if (evals % cfgs.getPopulationSize() == 0) {
				System.out.println("Best fitness value at evaluation " + Integer.toString(evals) + ": "
						+ Double.toString(Inits.maxScore));
			}
			// Select parents
			Sels.sortbyColumn(population, cfgs.getDimension());
			// System.out.println(Arrays.toString(population));

			int[] parentsInd = Sels.parentSelection_Tournament(population);

			// int[] parentsInd = Sels.parentSelection_Elitism(population,
			// Initializations.RandomDistributions.UNIFORM);
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
							population = Vars.wholeArithmeticCrossOver(population, population[parentsInd[i]],
									population[parentsInd[i + 1]]);
						}
					}
				}
				// population = Vars.order1CrossOver(population,
				// population[parentsInd[i]], population[parentsInd[i + 1]]);
				// population = Vars.singleArithmeticCrossOver(population,
				// population[parentsInd[i]], population[parentsInd[i + 1]]);
				// population = Vars.simpleArithmeticCrossOver(population,
				// population[parentsInd[i]], population[parentsInd[i + 1]]);
				// population = Vars.blendCrossOver(population,
				// population[parentsInd[i]], population[parentsInd[i + 1]]);
				// population = Vars.wholeArithmeticCrossOver(population,
				// population[parentsInd[i]], population[parentsInd[i + 1]]);
			}
			// Apply mutation
			for (int i = cfgs.getPopulationSize(); i < cfgs.getPopulationSize() + cfgs.getParentSelected(); i++) {
				if (new Random().nextDouble() < cfgs.getMutationRate()) {
					if (!isMultimodal) {
						if (!hasStructure) {
							if (!isSeparable) {
								// set the mutation operator for BentCigar
								Vars.customizedMutation(population[i]);
							}
						}
					}
					if (isMultimodal) {
						if (!hasStructure) {
							if (!isSeparable) {
								// set the mutation operator for KatsuuraEvaluation
								// Vars.singleUncorrelatedMutation(population[i]);
								Vars.correlatedMutation(population[i]);
							}
						}
						if (hasStructure) {
							if (!isSeparable) {
								// set the mutation operator for SchaffersEvaluation
								// Vars.multiUncorrelatedMutation(population[i]);
								Vars.correlatedMutation(population[i]);
							}
						}
					}
					// Vars.rnd_swap(population[i]);
					// Vars.uniformMutation(population[i]);
					// Vars.customizedMutation(population[i]);
					// Vars.nonUniformMutation(population[i]);
					// Vars.singleUncorrelatedMutation(population[i]);
					// Vars.multiUncorrelatedMutation(population[i]);
					// Vars.correlatedMutation(population[i]);
				}
			}
			// Check fitness of unknown fuction
			for (int i = 0; i < cfgs.getParentSelected(); i++) {
				double[] tempPop = Arrays.copyOfRange(population[cfgs.getPopulationSize() + i], 0, cfgs.getDimension());
				double tempEval = (double) evaluation_.evaluate(tempPop);
				population[cfgs.getPopulationSize() + i][cfgs.getDimension()] = tempEval;
				if (cfgs.getDEBUG()) {
					if (tempEval >= Inits.maxScore) {
						Inits.maxScore = tempEval;
					}
				}
				evals++;
			}
			// Select survivors
			population = Sels.survSelection_Elitism(population);
		}
		System.out.println(
				"Best fitness value at evaluation " + Integer.toString(evals) + ": " + Double.toString(Inits.maxScore));
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < population[0].length - 1; j++) {
				// System.out.print(population[i][j] + " ");
				if (population[i][j] > 5 || population[i][j] < -5) {
					System.out.print(population[i][j] + " ");
					System.out.println("Warning!"); // check if any value goes out of [-5,5]
				}
			}
		}
	}

}
