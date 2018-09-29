import java.util.*;

public class Variations {
	private Configs cfgs;

	public Variations(Configs cfgs) {
		this.cfgs = cfgs;
	}

	/**
	 * Random swap mutation operator.
	 * 
	 * @param individual
	 */
	public void rnd_swap(double[] individual) {

		// take 2 idx numbers between 0 and 9
		int idx1 = new Random().nextInt(9);
		int idx2 = new Random().nextInt(9);

		// check for uniqueness else change
		while (idx1 == idx2) {
			idx2 = new Random().nextInt(9);
		}

		// switch numbers
		double temp = individual[idx1];
		individual[idx1] = individual[idx2];
		individual[idx2] = temp;

	}

	/**
	 * Uniform mutation operator.
	 * 
	 * @param individual
	 */
	public void uniformMutation(double[] individual) {
		for (int i = 0; i < cfgs.getDimension(); i++) {
			if (new Random().nextInt((int) (1 / cfgs.getMutationRate())) == 0) {
				individual[i] = new Random().nextDouble() * (this.cfgs.getUpperBound() - this.cfgs.getLowerBound())
						+ this.cfgs.getLowerBound();
			}
		}
	}

	/**
	 * Non-uniform mutation operator.
	 * 
	 * @param individual
	 */
	public void nonUniformMutation(double[] individual) {
		for (int i = 0; i < cfgs.getDimension(); i++) {
			individual[i] = individual[i] + new Random().nextGaussian() * this.cfgs.getMutationStepSize();
		}
	}

	/**
	 * Older version of non-uniform mutation operator(wrondly implmented).
	 * 
	 * @param individual
	 */
	public void customizedMutation(double[] individual) {
		int[] mutationInds = new int[this.cfgs.getMutationSize()];
		for (int i = 0; i < this.cfgs.getMutationSize(); i++) {
			mutationInds[i] = new Random().nextInt(this.cfgs.getDimension());
			if (i != 0) {
				boolean overlapping = true;
				while (overlapping) {
					overlapping = false;
					for (int j = 0; j < i; j++) {
						if (mutationInds[i] == mutationInds[j]) {
							mutationInds[i] = new Random().nextInt(this.cfgs.getDimension());
							overlapping = true;
							break;
						}
					}
				}
			}
		}
		for (int mutationInd : mutationInds) {
			individual[mutationInd] = individual[mutationInd]
					+ new Random().nextGaussian() * this.cfgs.getMutationStepSize();
		}
	}

	/**
	 * Self-adaptive uncorrelated mutation with single mutation step size.
	 * 
	 * @param individual
	 */
	public void singleUncorrelatedMutation(double[] individual) {
		cfgs.setMutationStepSize(
				cfgs.getMutationStepSize() * Math.exp(cfgs.getMutationLearningRate() * new Random().nextGaussian()));
		if (cfgs.getMutationStepSize() < cfgs.getMutationStepSizeBound()) {
			cfgs.setMutationStepSize(cfgs.getMutationStepSizeBound());
		}
		for (int i = 0; i < cfgs.getDimension(); i++) {
			individual[i] = individual[i] + cfgs.getMutationStepSize() * new Random().nextGaussian();
		}
	}

	/**
	 * Self-adaptive uncorrelated mutation with multiple mutation step sizes.
	 * 
	 * @param individual
	 */
	public void multiUncorrelatedMutation(double[] individual) {
		double[] ndMutationStepSize = cfgs.getNdMutationStepSize();
		double overallNormalDist = new Random().nextGaussian();
		for (int i = 0; i < cfgs.getDimension(); i++) {
			ndMutationStepSize[i] = ndMutationStepSize[i] * Math.exp(cfgs.getMutationStepSize() * overallNormalDist
					+ cfgs.getSecondaryMutationLearningRate() * new Random().nextGaussian());
			if (ndMutationStepSize[i] < cfgs.getMutationStepSizeBound()) {
				ndMutationStepSize[i] = cfgs.getMutationStepSizeBound();
			}
			individual[i] = individual[i] + ndMutationStepSize[i] * new Random().nextGaussian();
		}
		// System.out.println(Arrays.toString(cfgs.getNdMutationStepSize()));
	}

	/**
	 * Single arithmetic Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] singleArithmeticCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		int k = new Random().nextInt(this.cfgs.getDimension());
		for (int i = 0; i < k; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		child1[k] = this.cfgs.getMixingFactor() * parent2[k] + (1 - this.cfgs.getMixingFactor()) * parent1[k];
		child2[k] = this.cfgs.getMixingFactor() * parent1[k] + (1 - this.cfgs.getMixingFactor()) * parent2[k];

		for (int i = k + 1; i < this.cfgs.getDimension(); i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Simple arithmetic Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] simpleArithmeticCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		int k = new Random().nextInt(this.cfgs.getDimension());
		for (int i = 0; i < k; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = k; i < this.cfgs.getDimension(); i++) {
			child1[i] = this.cfgs.getMixingFactor() * parent2[i] + (1 - this.cfgs.getMixingFactor()) * parent1[i];
			child2[i] = this.cfgs.getMixingFactor() * parent1[i] + (1 - this.cfgs.getMixingFactor()) * parent2[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Whole arithmetic Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] wholeArithmeticCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		double sum_1 = 0;
		double sum_2 = 0;
		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			sum_1 = sum_1 + parent1[i];
			sum_2 = sum_2 + parent2[i];
		}
		double mean_1 = sum_1 / this.cfgs.getDimension();
		double mean_2 = sum_2 / this.cfgs.getDimension();

		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			child1[i] = this.cfgs.getMixingFactor() * mean_1 + (1 - this.cfgs.getMixingFactor()) * mean_2;
			child2[i] = this.cfgs.getMixingFactor() * mean_2 + (1 - this.cfgs.getMixingFactor()) * mean_1;
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Blend Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] blendCrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		for (int i = 0; i < this.cfgs.getDimension(); i++) {
			double d = Math.abs(parent1[i] - parent2[i]);
			child1[i] = (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d)
					+ new Random().nextDouble() * ((Math.min(parent1[i], parent2[i]) + this.cfgs.getMixingFactor() * d)
							- (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d));

			child2[i] = (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d)
					+ new Random().nextDouble() * ((Math.min(parent1[i], parent2[i]) + this.cfgs.getMixingFactor() * d)
							- (Math.min(parent1[i], parent2[i]) - this.cfgs.getMixingFactor() * d));
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;

		return renewedPopulation;
	}

	/**
	 * Order-one Crossover operator.
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @return Renewed population with the children.
	 */
	public double[][] order1CrossOver(double[][] population, double[] parent1, double[] parent2) {
		double[][] renewedPopulation = new double[population.length + 2][population[0].length];
		double[] child1 = new double[population[0].length];
		double[] child2 = new double[population[0].length];

		int startInd = new Random().nextInt(this.cfgs.getDimension());
		int endInd = new Random().nextInt(this.cfgs.getDimension() - startInd) + startInd;

		for (int i = 0; i < startInd; i++) {
			child1[i] = parent2[i];
			child2[i] = parent1[i];
		}

		for (int i = startInd; i < endInd; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = endInd; i < this.cfgs.getDimension(); i++) {
			child1[i] = parent2[i];
			child2[i] = parent1[i];
		}

		for (int i = 0; i < population.length; i++) {
			renewedPopulation[i] = population[i];
		}
		renewedPopulation[population.length] = child1;
		renewedPopulation[population.length + 1] = child2;
		return renewedPopulation;
	}

}
