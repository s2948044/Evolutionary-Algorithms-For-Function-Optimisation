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
	 * 
	 * @param population
	 * @param parent1
	 * @param parent2
	 * @param Inits
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
