import java.util.*;

public class Variations {
	private Configs _Cfgs;

	public Variations(Configs Cfgs) {
		_Cfgs = Cfgs;
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
		int[] mutationInds = new int[_Cfgs.getmutationSize()];
		for (int i = 0; i < _Cfgs.getmutationSize(); i++) {
			mutationInds[i] = new Random().nextInt(_Cfgs.getdimension());
			if (i != 0) {
				boolean overlapping = true;
				while (overlapping) {
					overlapping = false;
					for (int j = 0; j < i; j++) {
						if (mutationInds[i] == mutationInds[j]) {
							mutationInds[i] = new Random().nextInt(_Cfgs.getdimension());
							overlapping = true;
							break;
						}
					}
				}
			}
		}
		for (int mutationInd : mutationInds) {
			individual[mutationInd] = new Random().nextDouble() * (_Cfgs.getupperBound() - _Cfgs.getlowerBound())
					+ _Cfgs.getlowerBound();
		}
	}

	/**
	 * Non-uniform mutation operator.
	 * 
	 * @param individual
	 */
	public void nonUniformMutation(double[] individual) {
		int[] mutationInds = new int[_Cfgs.getmutationSize()];
		for (int i = 0; i < _Cfgs.getmutationSize(); i++) {
			mutationInds[i] = new Random().nextInt(_Cfgs.getdimension());
			if (i != 0) {
				boolean overlapping = true;
				while (overlapping) {
					overlapping = false;
					for (int j = 0; j < i; j++) {
						if (mutationInds[i] == mutationInds[j]) {
							mutationInds[i] = new Random().nextInt(_Cfgs.getdimension());
							overlapping = true;
							break;
						}
					}
				}
			}
		}
		for (int mutationInd : mutationInds) {
			individual[mutationInd] = individual[mutationInd]
					+ new Random().nextGaussian() * _Cfgs.getmutationStepSize();
		}
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

		int k = new Random().nextInt(_Cfgs.getdimension());
		for (int i = 0; i < k; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		child1[k] = _Cfgs.getmixingFactor() * parent2[k] + (1 - _Cfgs.getmixingFactor()) * parent1[k];
		child2[k] = _Cfgs.getmixingFactor() * parent1[k] + (1 - _Cfgs.getmixingFactor()) * parent2[k];

		for (int i = k + 1; i < _Cfgs.getdimension(); i++) {
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

		int k = new Random().nextInt(_Cfgs.getdimension());
		for (int i = 0; i < k; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = k; i < _Cfgs.getdimension(); i++) {
			child1[i] = _Cfgs.getmixingFactor() * parent2[i] + (1 - _Cfgs.getmixingFactor()) * parent1[i];
			child2[i] = _Cfgs.getmixingFactor() * parent1[i] + (1 - _Cfgs.getmixingFactor()) * parent2[i];
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
		for (int i = 0; i < _Cfgs.getdimension(); i++) {
			sum_1 = sum_1 + parent1[i];
			sum_2 = sum_2 + parent2[i];
		}
		double mean_1 = sum_1 / _Cfgs.getdimension();
		double mean_2 = sum_2 / _Cfgs.getdimension();

		for (int i = 0; i < _Cfgs.getdimension(); i++) {
			child1[i] = _Cfgs.getmixingFactor() * mean_1 + (1 - _Cfgs.getmixingFactor()) * mean_2;
			child2[i] = _Cfgs.getmixingFactor() * mean_2 + (1 - _Cfgs.getmixingFactor()) * mean_1;
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

		for (int i = 0; i < _Cfgs.getdimension(); i++) {
			double d = Math.abs(parent1[i] - parent2[i]);
			child1[i] = (Math.min(parent1[i], parent2[i]) - _Cfgs.getmixingFactor() * d)
					+ new Random().nextDouble() * ((Math.min(parent1[i], parent2[i]) + _Cfgs.getmixingFactor() * d)
							- (Math.min(parent1[i], parent2[i]) - _Cfgs.getmixingFactor() * d));

			child2[i] = (Math.min(parent1[i], parent2[i]) - _Cfgs.getmixingFactor() * d)
					+ new Random().nextDouble() * ((Math.min(parent1[i], parent2[i]) + _Cfgs.getmixingFactor() * d)
							- (Math.min(parent1[i], parent2[i]) - _Cfgs.getmixingFactor() * d));
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

		int startInd = new Random().nextInt(_Cfgs.getdimension());
		int endInd = new Random().nextInt(_Cfgs.getdimension() - startInd) + startInd;

		for (int i = 0; i < startInd; i++) {
			child1[i] = parent2[i];
			child2[i] = parent1[i];
		}

		for (int i = startInd; i < endInd; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}

		for (int i = endInd; i < _Cfgs.getdimension(); i++) {
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
